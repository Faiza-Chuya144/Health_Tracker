package seu.edu.bd.healthtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import seu.edu.bd.healthtracker.service.FoodScannerService; // Import the new service

import java.io.IOException;
import java.util.Map;

@Controller
public class FoodScannerController {

    private final FoodScannerService foodScannerService; // Inject the service

    public FoodScannerController(FoodScannerService foodScannerService) {
        this.foodScannerService = foodScannerService;
    }

    @GetMapping("/food_scanner")
    public String foodScannerPage() {
        return "food_scanner";
    }

    @PostMapping("/food_scanner/analyze")
    public String analyzeFood(@RequestParam("foodImageFile") MultipartFile file, Model model) {
        try {
            Map<String, String> analysisData = foodScannerService.processFoodImage(file); // Call the service method

            if (analysisData.containsKey("errorMessage")) {
                model.addAttribute("errorMessage", analysisData.get("errorMessage"));
            } else {
                model.addAttribute("analysisResult", analysisData.get("analysisResult"));
                model.addAttribute("fileName", analysisData.get("fileName"));
            }

        } catch (IOException e) {
            model.addAttribute("errorMessage", "Error processing food image: " + e.getMessage());
            e.printStackTrace();
        }
        return "food_scanner";
    }
}