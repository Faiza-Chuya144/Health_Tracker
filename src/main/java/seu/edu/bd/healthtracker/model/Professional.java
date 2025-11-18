package seu.edu.bd.healthtracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "professionals")
public class Professional {

    @Id
    private String id;
    private String fullName;
    private String specialty;
    private String avatarUrl; // Optional field for avatar image URL
    private LocalDateTime createdAt;

    public Professional() {
        this.createdAt = LocalDateTime.now(); // Set creation time automatically
    }

    public Professional(String fullName, String specialty, String avatarUrl) {
        this.fullName = fullName;
        this.specialty = specialty;
        this.avatarUrl = avatarUrl;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Professional{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", specialty='" + specialty + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
