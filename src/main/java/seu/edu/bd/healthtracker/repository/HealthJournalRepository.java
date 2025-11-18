package seu.edu.bd.healthtracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import seu.edu.bd.healthtracker.model.UserDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HealthJournalRepository extends MongoRepository<UserDetails, String> {
    Optional<UserDetails> findByUserNameAndEntryDate(String userName, LocalDate entryDate);
    List<UserDetails> findByUserName(String userName);
    void deleteByUserNameAndEntryDate(String userName, LocalDate entryDate);
}
