package seu.edu.bd.healthtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seu.edu.bd.healthtracker.service.UserManagementService; // Using the new service

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/users") // Base mapping for user management
public class UserManagementController {

    private final UserManagementService userManagementService;

    // Constructor injection for UserManagementService
    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }


    @GetMapping
    public String viewUserManagement(Model model) {
        // Retrieve a list of users with their aggregated data from the service layer.
        List<Map<String, Object>> usersData = userManagementService.getAllUsersWithAggregatedData();
        // Add the usersData list to the model, so it can be accessed in the Thymeleaf template.
        model.addAttribute("usersData", usersData);
        // Return the name of the Thymeleaf template to render.
        return "user_management"; // This will map to src/main/resources/templates/user_management.html
    }


    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
        try {
            // Call the service to delete the user by their ID.
            userManagementService.deleteUserById(id);
            // Add a success message to be displayed on the redirected page.
            redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (Exception e) {
            // If an error occurs, add an error message.
            redirectAttributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
        }
        // Redirect back to the user management page to show the updated list.
        return "redirect:/admin/users";
    }
}
