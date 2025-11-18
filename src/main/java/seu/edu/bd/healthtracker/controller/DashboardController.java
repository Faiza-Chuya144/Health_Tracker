/*package seu.edu.bd.healthtracker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import seu.edu.bd.healthtracker.model.User;
import seu.edu.bd.healthtracker.model.UserDetails;
import seu.edu.bd.healthtracker.repository.UserRepository;
import seu.edu.bd.healthtracker.repository.HealthJournalRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController {

    private final UserRepository userRepository;
    private final HealthJournalRepository healthJournalRepository;

    public DashboardController(UserRepository userRepository, HealthJournalRepository healthJournalRepository) {
        this.userRepository = userRepository;
        this.healthJournalRepository = healthJournalRepository;
    }

    @GetMapping("/dashboard")
    public String dashboardRedirect(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/user/dashboard";
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            model.addAttribute("userDetails", optionalUser.get());
            return "admin_dashboard";
        } else {
            return "redirect:/login?error=userNotFound";
        }
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?error=userNotFound";
        }

        User user = optionalUser.get();

        // Get all health journal entries for the user
        List<UserDetails> journalList = healthJournalRepository.findByUserName(user.getUserName());

        model.addAttribute("userDetails", user);

        if (!journalList.isEmpty()) {
            // You can either send all entries or just the latest one
            model.addAttribute("healthJournal", journalList.get(0)); // or journalList
        } else {
            model.addAttribute("healthJournal", null);
        }

        return "user_dashboard";
    }

}*/

package seu.edu.bd.healthtracker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import seu.edu.bd.healthtracker.model.User;
import seu.edu.bd.healthtracker.model.UserDetails;
import seu.edu.bd.healthtracker.repository.UserRepository;
import seu.edu.bd.healthtracker.repository.HealthJournalRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class DashboardController {

    private final UserRepository userRepository;
    private final HealthJournalRepository healthJournalRepository;

    public DashboardController(UserRepository userRepository, HealthJournalRepository healthJournalRepository) {
        this.userRepository = userRepository;
        this.healthJournalRepository = healthJournalRepository;
    }

    @GetMapping("/dashboard")
    public String dashboardRedirect(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/user/dashboard";
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?error=userNotFound";
        }

        User user = optionalUser.get();

        // Get all health journal entries for the user
        List<UserDetails> journalList = healthJournalRepository.findByUserName(user.getUserName());

        model.addAttribute("userDetails", user);

        if (!journalList.isEmpty()) {
            // You can either send all entries or just the latest one
            model.addAttribute("healthJournal", journalList.get(0)); // or journalList
        } else {
            model.addAttribute("healthJournal", null);
        }

        return "admin_dashboard";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?error=userNotFound";
        }

        User user = optionalUser.get();

        // Get all health journal entries for the user
        List<UserDetails> journalList = healthJournalRepository.findByUserName(user.getUserName());

        model.addAttribute("userDetails", user);

        if (!journalList.isEmpty()) {
            // You can either send all entries or just the latest one
            model.addAttribute("healthJournal", journalList.get(0)); // or journalList
        } else {
            model.addAttribute("healthJournal", null);
        }

        return "user_dashboard";
    }

}

