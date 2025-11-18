package seu.edu.bd.healthtracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import seu.edu.bd.healthtracker.model.User;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    void deleteByUserId(String userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}
