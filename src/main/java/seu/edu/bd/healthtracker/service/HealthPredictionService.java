package seu.edu.bd.healthtracker.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import seu.edu.bd.healthtracker.model.Recommendation;
import seu.edu.bd.healthtracker.model.UserDetails;

import java.util.*;

@Service
public class HealthPredictionService {

    private final HealthJournalService healthJournalService;
    private final ChatClient chatClient;

    public HealthPredictionService(HealthJournalService healthJournalService, ChatClient.Builder chatClientBuilder) {
        this.healthJournalService = healthJournalService;
        this.chatClient = chatClientBuilder.build();
    }

    public Map<String, Object> getDashboardMetrics(String userName) {
        List<UserDetails> journalList = healthJournalService.findByUserName(userName);

        if (journalList.isEmpty()) {
            return getEmptyDashboard();
        }

        int healthScore = calculateHealthScore(journalList);
        String riskLevel = assessRisk(healthScore);

        String forecastText = generateAIResponse(journalList, "Give health forecast in 3 bullet points.");
        String recommendationText = generateAIResponse(journalList, "Suggest 3 personalized health tips with priority levels (low, medium, high).");

        List<String> forecast = Arrays.asList(forecastText.split("\\n"));
        List<Recommendation> recommendations = parseRecommendations(recommendationText);

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("healthScore", healthScore);
        dashboard.put("riskLevel", riskLevel);
        dashboard.put("forecast", forecast);
        dashboard.put("recommendations", recommendations);
        return dashboard;
    }

    private String generateAIResponse(List<UserDetails> journalList, String instruction) {
        StringBuilder prompt = new StringBuilder();
        prompt.append(instruction).append("\n");
        prompt.append("Format: Date, Age, Weight, Height, BMI, Health Issues\n");

        for (UserDetails journal : journalList) {
            prompt.append(String.format("%s, %d, %.1f, %.1f, %.1f, %s\n",
                    journal.getEntryDate(),
                    journal.getAge(),
                    journal.getWeight(),
                    journal.getHeight(),
                    journal.getBmi(),
                    journal.getHealthIssues().toString().replaceAll("[{}]", "")));
        }

        ChatResponse response = chatClient.prompt().user(prompt.toString()).call().chatResponse();
        return response.getResult().getOutput().getText();
    }

    private int calculateHealthScore(List<UserDetails> journals) {
        return (int) journals.stream().mapToDouble(j -> {
            double bmi = j.getBmi() > 0 ? j.getBmi() : 22.0;
            double score = 100.0;
            score -= Math.abs(bmi - 22) * 2.5;

            if (j.getHealthIssues() != null) {
                if (j.getHealthIssues().containsKey("Blood Pressure")) score -= 5;
                if (j.getHealthIssues().containsKey("Diabetes")) score -= 5;
            }

            return Math.max(0, score);
        }).average().orElse(60);
    }

    private String assessRisk(int score) {
        if (score >= 80) return "Low Risk";
        if (score >= 60) return "Moderate Risk";
        return "High Risk";
    }

    private List<Recommendation> parseRecommendations(String text) {
        List<Recommendation> recs = new ArrayList<>();
        String[] lines = text.split("\\n");
        for (String line : lines) {
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                String priority = line.toLowerCase().contains("high") ? "high" :
                        line.toLowerCase().contains("medium") ? "medium" : "low";

                recs.add(new Recommendation(parts[0].trim(), parts[1].trim(), priority));
            }
        }
        return recs;
    }

    private Map<String, Object> getEmptyDashboard() {
        Map<String, Object> empty = new HashMap<>();
        empty.put("healthScore", 50);
        empty.put("riskLevel", "Moderate Risk");
        empty.put("forecast", List.of("Not enough data for forecast"));
        empty.put("recommendations", List.of(
                new Recommendation("Update Journal", "Please log your health data to get predictions", "high")
        ));
        return empty;
    }

}
