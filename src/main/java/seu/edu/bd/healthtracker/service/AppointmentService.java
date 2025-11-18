package seu.edu.bd.healthtracker.service;

import org.springframework.stereotype.Service;
import seu.edu.bd.healthtracker.model.Appointment; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/model/Appointment.java
import seu.edu.bd.healthtracker.model.Professional; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/model/Professional.java
import seu.edu.bd.healthtracker.model.User; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/model/User.java
import seu.edu.bd.healthtracker.repository.AppointmentRepository; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/repository/AppointmentRepository.java
import seu.edu.bd.healthtracker.repository.ProfessionalRepository; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/repository/ProfessionalRepository.java
import seu.edu.bd.healthtracker.repository.UserRepository; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/repository/UserRepository.java

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
public class AppointmentService { //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java

    private final AppointmentRepository appointmentRepository; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
    private final ProfessionalRepository professionalRepository; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
    private final UserRepository userRepository; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java

    public AppointmentService(AppointmentRepository appointmentRepository, ProfessionalRepository professionalRepository, UserRepository userRepository) { //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
        this.appointmentRepository = appointmentRepository;
        this.professionalRepository = professionalRepository;
        this.userRepository = userRepository;
    }

    public List<Professional> getAllProfessionals() { //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
        return professionalRepository.findAll();
    }

    public Appointment bookAppointment(String professionalId, LocalDate appointmentDate, String appointmentTime, String patientUserId) { //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
        Optional<Professional> professionalOptional = professionalRepository.findById(professionalId); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
        Optional<User> patientOptional = userRepository.findById(patientUserId); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java

        if (professionalOptional.isPresent() && patientOptional.isPresent()) { //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
            Professional professional = professionalOptional.get(); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
            User patient = patientOptional.get(); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java

            List<Appointment> existingAppointments = appointmentRepository.findByProfessionalIdAndAppointmentDate(professionalId, appointmentDate); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
            // In a real app, you'd check for time conflicts here. For simplicity, we'll just book.

            Appointment newAppointment = new Appointment( //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
                    patient.getId(),
                    patient.getUserName(),
                    professional.getId(),
                    professional.getFullName(), // Corrected to getFullName()
                    professional.getSpecialty(),
                    appointmentDate,
                    appointmentTime,
                    "Confirmed"
            );

            newAppointment.setConfirmationSlipId(UUID.randomUUID().toString().replace("-", "")); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java

            return appointmentRepository.save(newAppointment); //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
        }
        return null; //cite: Health Tracker/src/main/java/seu/edu/bd/healthtracker/service/AppointmentService.java
    }
}