package seu.edu.bd.healthtracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import seu.edu.bd.healthtracker.model.Professional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends MongoRepository<Professional, String> {

    // Custom query methods (optional, MongoRepository provides basic CRUD)
    Optional<Professional> findByFullName(String fullName);
    List<Professional> findAllByOrderByCreatedAtDesc(); // Order by creation date for recent additions

}
