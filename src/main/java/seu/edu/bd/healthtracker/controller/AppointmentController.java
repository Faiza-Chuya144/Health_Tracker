package seu.edu.bd.healthtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seu.edu.bd.healthtracker.model.Appointment; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/model/Appointment.java
import seu.edu.bd.healthtracker.model.Professional; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/model/Professional.java
import seu.edu.bd.healthtracker.model.User; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/model/User.java
import seu.edu.bd.healthtracker.service.AppointmentService; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
import seu.edu.bd.healthtracker.service.ProfessionalService; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/ProfessionalService.java
import seu.edu.bd.healthtracker.service.UserService; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/UserService.java

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class AppointmentController {

    private final AppointmentService appointmentService; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
    private final ProfessionalService professionalService; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
    private final UserService userService; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java

    @Autowired
    public AppointmentController(AppointmentService appointmentService, ProfessionalService professionalService, UserService userService) { //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
        this.appointmentService = appointmentService;
        this.professionalService = professionalService;
        this.userService = userService;
    }

    @GetMapping("/book_appointment")
    public String showBookAppointmentPage(Model model, Authentication authentication) { //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
        List<Professional> professionals = professionalService.findAllProfessionals();
        model.addAttribute("professionals", professionals);

        if (authentication != null && authentication.isAuthenticated()) {
            String currentUsernameOrEmail = authentication.getName();
            Optional<User> currentUserOptional = userService.findByEmail(currentUsernameOrEmail);
            if (currentUserOptional.isPresent()) {
                User currentUser = currentUserOptional.get();
                model.addAttribute("patientUserId", currentUser.getId());
            }
        }
        return "book_appointment";
    }

    @PostMapping("/book_appointment/confirm")
    public String confirmAppointment(@RequestParam("professionalId") String professionalId, //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
                                     @RequestParam("appointmentDate") LocalDate appointmentDate, //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
                                     @RequestParam("appointmentTime") String appointmentTime, //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
                                     @RequestParam("patientUserId") String patientUserId, //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
                                     RedirectAttributes redirectAttributes) { //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java

        Appointment newAppointment = appointmentService.bookAppointment(professionalId, appointmentDate, appointmentTime, patientUserId); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java

        if (newAppointment != null) { //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
            redirectAttributes.addFlashAttribute("successBooking", true); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
            redirectAttributes.addFlashAttribute("bookedProfessionalName", newAppointment.getProfessionalName()); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
            redirectAttributes.addFlashAttribute("bookedAppointmentDate", newAppointment.getAppointmentDate().format(DateTimeFormatter.ofPattern("EEE, MMM dd,yyyy"))); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
            redirectAttributes.addFlashAttribute("bookedAppointmentTime", newAppointment.getAppointmentTime()); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
            return "redirect:/user/book_appointment"; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to book appointment. Professional or patient not found."); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
            return "redirect:/user/book_appointment"; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/controller/AppointmentController.java
        }
    }
}