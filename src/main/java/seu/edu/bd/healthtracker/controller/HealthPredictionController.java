package seu.edu.bd.healthtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping; // Add this import
import seu.edu.bd.healthtracker.model.User;
import seu.edu.bd.healthtracker.service.HealthPredictionService;
import seu.edu.bd.healthtracker.service.UserService;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/health_prediction")
public class HealthPredictionController {

    private final HealthPredictionService healthPredictionService;
    private final UserService userService;

    public HealthPredictionController(HealthPredictionService healthPredictionService, UserService userService) {
        this.healthPredictionService = healthPredictionService;
        this.userService = userService;
    }


    // Handles the initial GET request to display the page with only the button
    @GetMapping
    public String showHealthPredictionForm(Model model) {
        // Initially, set a flag to hide the prediction results
        model.addAttribute("showResults", false);
        return "health_prediction";
    }

    // Handles the POST request when "Generate Prediction" button is clicked
    @PostMapping
    public String generateHealthPrediction(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login"; // Redirect if not authenticated
        }

        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow();
        String userName = user.getUserName();

        Map<String, Object> dashboardData = healthPredictionService.getDashboardMetrics(userName);


        model.addAttribute("healthScore", dashboardData.get("healthScore"));
        model.addAttribute("riskLevel", dashboardData.get("riskLevel"));
        model.addAttribute("forecast", dashboardData.get("forecast"));
        model.addAttribute("recommendations", dashboardData.get("recommendations"));

        // Set the flag to true to show the prediction results
        model.addAttribute("showResults", true);

        return "health_prediction"; // Return the same template to display results
    }
}