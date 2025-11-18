package seu.edu.bd.healthtracker.service;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import seu.edu.bd.healthtracker.interfaces.UserInterface;
import seu.edu.bd.healthtracker.model.User;
import seu.edu.bd.healthtracker.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService, UserInterface {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public void registerUser(User user) {
        user.setRole(List.of("USER"));
        userRepository.save(user);
    }

    public void initAdmin() {
        Optional<User> adminOpt = userRepository.findByEmail("chuya@gmail.com");
        if (adminOpt.isEmpty()) {
            User admin = new User();
            admin.setUserName("Admin");
            admin.setEmail("chuya@gmail.com");
            admin.setPassword("123");
            admin.setRole(List.of("ADMIN"));
            userRepository.save(admin);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRole().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList())
        );
    }
}
