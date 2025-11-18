package seu.edu.bd.healthtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seu.edu.bd.healthtracker.model.Professional; // Using the Professional model directly
import seu.edu.bd.healthtracker.service.ProfessionalService;

import java.util.List;

@Controller
@RequestMapping("/admin/professionals") // Base mapping for professionals
public class ProfessionalController {

    private final ProfessionalService professionalService;

    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @GetMapping
    public String manageProfessionals(Model model) {
        List<Professional> professionals = professionalService.findAllProfessionals();
        model.addAttribute("professionals", professionals);
        // Changed to use Professional model directly for the form backing object
        model.addAttribute("newProfessional", new Professional());
        return "professionals"; // This will map to src/main/resources/templates/professionals.html
    }

    @PostMapping("/add")
    // Changed @ModelAttribute to bind directly to Professional model
    public String addProfessional(@ModelAttribute("newProfessional") Professional newProfessional,
                                  RedirectAttributes redirectAttributes) {
        // Basic validation and logic
        if (newProfessional.getFullName() == null || newProfessional.getFullName().trim().isEmpty() ||
                newProfessional.getSpecialty() == null || newProfessional.getSpecialty().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Full Name and Specialty are required.");
            return "redirect:/admin/professionals";
        }

        // Check if professional with this full name already exists
        if (professionalService.existsByFullName(newProfessional.getFullName())) {
            redirectAttributes.addFlashAttribute("error", "Professional with this name already exists.");
            return "redirect:/admin/professionals";
        }

        // The Professional object is already populated by @ModelAttribute
        // No need to create a new Professional object and copy properties
        professionalService.saveProfessional(newProfessional);
        redirectAttributes.addFlashAttribute("success", "Professional added successfully!");
        return "redirect:/admin/professionals";
    }

    @PostMapping("/delete")
    public String deleteProfessional(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
        professionalService.deleteProfessionalById(id);
        redirectAttributes.addFlashAttribute("success", "Professional deleted successfully!");
        return "redirect:/admin/professionals";
    }

    // You might want an /edit endpoint if you implement editing functionality
    // For now, the screenshot only shows add and delete.
}
