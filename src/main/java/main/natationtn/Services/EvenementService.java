package main.natationtn.Services;

import main.natationtn.Entities.Evenement;
import main.natationtn.Repositories.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvenementService {

    private final EvenementRepository evenementRepository;

    @Autowired
    public EvenementService(EvenementRepository evenementRepository) {
        this.evenementRepository = evenementRepository;
    }

    // Create
    public Evenement saveEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    // Read
    public List<Evenement> getAllEvenements() {
        return evenementRepository.findAll();
    }

    // ✅ Get All Paged
    public Page<Evenement> getAllEvenementsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return evenementRepository.findAll(pageable);
    }

    public Optional<Evenement> getEvenementById(Long id) {
        return evenementRepository.findById(id);
    }

    // Update
    public Evenement updateEvenement(Evenement evenement) {
        if (!evenementRepository.existsById(evenement.getId())) {
            throw new IllegalArgumentException("Evenement with ID " + evenement.getId() + " not found.");
        }
        return evenementRepository.save(evenement);
    }

    // Delete
    public void deleteEvenement(Long id) {
        if (!evenementRepository.existsById(id)) {
            throw new IllegalArgumentException("Evenement with ID " + id + " not found.");
        }
        evenementRepository.deleteById(id);
    }
}
