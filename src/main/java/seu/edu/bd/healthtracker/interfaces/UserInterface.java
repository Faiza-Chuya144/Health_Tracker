package seu.edu.bd.healthtracker.interfaces;


import seu.edu.bd.healthtracker.model.User;

import java.util.Optional;

public interface UserInterface {

    Optional<User> findByEmail(String email);

}
