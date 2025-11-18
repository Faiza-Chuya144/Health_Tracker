package seu.edu.bd.healthtracker.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import seu.edu.bd.healthtracker.model.User;
import seu.edu.bd.healthtracker.service.UserService;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user") User user, Model model) {
        userService.registerUser(user);
        model.addAttribute("message", "Registration successful. You can now login.");
        return "login";
    }
}