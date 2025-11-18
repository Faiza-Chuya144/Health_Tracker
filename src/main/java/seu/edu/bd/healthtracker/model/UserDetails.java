package seu.edu.bd.healthtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;

@Document("user_details")
public class UserDetails {

    @Id
    private String id;
    private String userName;
    private int age;
    private double weight;
    private double height;
    private double bmi;

    // Map<HealthIssue, Score>
    private Map<String, String> healthIssues;

    private LocalDate entryDate;

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getBmi() { return bmi; }
    public void setBmi(double bmi) { this.bmi = bmi; }

    public Map<String, String> getHealthIssues() { return healthIssues; }
    public void setHealthIssues(Map<String, String> healthIssues) { this.healthIssues = healthIssues; }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }
}
