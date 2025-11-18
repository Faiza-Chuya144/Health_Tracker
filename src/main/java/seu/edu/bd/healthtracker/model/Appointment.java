package seu.edu.bd.healthtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "appointments")
public class Appointment {
    @Id
    private String id;
    private String patientUserId; // ID of the user booking the appointment
    private String patientName;
    private String professionalId; // ID of the professional
    private String professionalName;
    private String professionalSpecialty;
    private LocalDate appointmentDate;
    private String appointmentTime; // Storing as String for simplicity (e.g., "10:00 AM")
    private String status; // e.g., "Pending", "Confirmed", "Cancelled", "Completed"
    private LocalDateTime bookingDateTime; // Timestamp of when the appointment was booked
    private String confirmationSlipId; // Unique ID for the confirmation slip

    // Constructor
    public Appointment(String patientUserId, String patientName, String professionalId, String professionalName, String professionalSpecialty, LocalDate appointmentDate, String appointmentTime, String status) {
        this.patientUserId = patientUserId;
        this.patientName = patientName;
        this.professionalId = professionalId;
        this.professionalName = professionalName;
        this.professionalSpecialty = professionalSpecialty;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.bookingDateTime = LocalDateTime.now(); // Set booking time to now
        this.confirmationSlipId = null;
    }

    // Default constructor for Spring Data MongoDB
    public Appointment() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientUserId() {
        return patientUserId;
    }

    public void setPatientUserId(String patientUserId) {
        this.patientUserId = patientUserId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public String getProfessionalSpecialty() {
        return professionalSpecialty;
    }

    public void setProfessionalSpecialty(String professionalSpecialty) {
        this.professionalSpecialty = professionalSpecialty;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getConfirmationSlipId() {
        return confirmationSlipId;
    }

    public void setConfirmationSlipId(String confirmationSlipId) {
        this.confirmationSlipId = confirmationSlipId;
    }
}