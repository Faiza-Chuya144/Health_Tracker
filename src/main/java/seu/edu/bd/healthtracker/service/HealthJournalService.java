package seu.edu.bd.healthtracker.service;

import org.springframework.stereotype.Service;
import seu.edu.bd.healthtracker.model.UserDetails;
import seu.edu.bd.healthtracker.repository.HealthJournalRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HealthJournalService {

    private final HealthJournalRepository journalRepository;

    public HealthJournalService(HealthJournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public void saveOrUpdate(UserDetails details) {
        journalRepository.save(details);
    }

    public Optional<UserDetails> findByUserNameAndEntryDate(String userName, LocalDate entryDate) {
        return journalRepository.findByUserNameAndEntryDate(userName, entryDate);
    }

    public List<UserDetails> findByUserName(String userName) {
        return journalRepository.findByUserName(userName);
    }

    public void delete(String userName, LocalDate entryDate) {
        journalRepository.deleteByUserNameAndEntryDate(userName, entryDate);
    }


}
