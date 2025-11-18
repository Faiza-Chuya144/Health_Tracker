package seu.edu.bd.healthtracker.service;

import org.springframework.stereotype.Service;
import seu.edu.bd.healthtracker.model.User;
import seu.edu.bd.healthtracker.model.UserDetails; // Assuming this is your HealthJournal entity
import seu.edu.bd.healthtracker.repository.UserRepository;
import seu.edu.bd.healthtracker.repository.HealthJournalRepository; // To get journal entries

import java.time.LocalDate;
import java.time.format.DateTimeFormatter; // Import DateTimeFormatter
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserManagementService {

    private final UserRepository userRepository;
    private final HealthJournalRepository healthJournalRepository; // Assuming this exists for journal data

    public UserManagementService(UserRepository userRepository, HealthJournalRepository healthJournalRepository) {
        this.userRepository = userRepository;
        this.healthJournalRepository = healthJournalRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
        // Optional: Also delete associated journal entries if user is deleted
        // healthJournalRepository.deleteByUserName(user.getUserName()); // You might need a method like this
    }

    // This method will aggregate user data for display on the management page
    public List<Map<String, Object>> getAllUsersWithAggregatedData() {
        List<User> users = userRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define your desired format

        return users.stream().map(user -> {
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("username", user.getUserName()); // Assuming userName is available
            userData.put("email", user.getEmail());

            // Fetch journal entries for this user
            List<UserDetails> journalEntries = healthJournalRepository.findByUserName(user.getUserName()); // Assuming findByUserName exists

            userData.put("journalEntriesCount", journalEntries.size());

            // Determine last entry date and format it to String
            String lastEntryDateFormatted = journalEntries.stream()
                    .map(UserDetails::getEntryDate)
                    .max(LocalDate::compareTo)
                    .map(date -> date.format(formatter)) // Format LocalDate to String here
                    .orElse("N/A"); // Default to "N/A" if no entries
            userData.put("lastEntryDate", lastEntryDateFormatted);

            // Placeholder for conditions (you might fetch this from another entity/logic)
            userData.put("conditions", "N/A"); // Replace with actual logic if available

            return userData;
        }).collect(Collectors.toList());
    }
}
