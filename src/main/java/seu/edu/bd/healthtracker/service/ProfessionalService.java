package seu.edu.bd.healthtracker.service;

import org.springframework.stereotype.Service;
import seu.edu.bd.healthtracker.model.Professional;
import seu.edu.bd.healthtracker.repository.ProfessionalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public List<Professional> findAllProfessionals() {
        return professionalRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<Professional> findProfessionalById(String id) {
        return professionalRepository.findById(id);
    }

    public Professional saveProfessional(Professional professional) {
        return professionalRepository.save(professional);
    }

    public void deleteProfessionalById(String id) {
        professionalRepository.deleteById(id);
    }

    public boolean existsByFullName(String fullName) {
        return professionalRepository.findByFullName(fullName).isPresent();
    }
}
