package seu.edu.bd.healthtracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import seu.edu.bd.healthtracker.model.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    List<Appointment> findByProfessionalIdAndAppointmentDate(String professionalId, LocalDate appointmentDate);
    List<Appointment> findByPatientUserId(String patientUserId);
    Optional<Appointment> findByConfirmationSlipId(String confirmationSlipId);
}