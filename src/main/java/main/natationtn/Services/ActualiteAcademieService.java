package main.natationtn.Services;

import jakarta.persistence.EntityNotFoundException;
import main.natationtn.Entities.ActualiteAcademie;
import main.natationtn.Entities.StatutPublication;
import main.natationtn.Repositories.ActualiteAcademieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ActualiteAcademieService {

    private final ActualiteAcademieRepository actualiteAcademieRepository;

    @Autowired
    public ActualiteAcademieService(ActualiteAcademieRepository actualiteAcademieRepository) {
        this.actualiteAcademieRepository = actualiteAcademieRepository;
    }

    public ActualiteAcademie createActualiteAcademie(ActualiteAcademie actualite) {
        return actualiteAcademieRepository.save(actualite);
    }
//    // Create
//    public ActualiteAcademie saveActualiteAcademie(ActualiteAcademie actualiteAcademie) {
//        return actualiteAcademieRepository.save(actualiteAcademie);
//    }

    // Read
    public List<ActualiteAcademie> getAllActualiteAcademies() {
        return actualiteAcademieRepository.findAll();
    }

    public Optional<ActualiteAcademie> getActualiteAcademieById(Long id) {
        return actualiteAcademieRepository.findById(id);
    }

    public List<ActualiteAcademie> getActualiteAcademiesByStatut(StatutPublication statutPublication) {
        return actualiteAcademieRepository.findByStatutPublication(statutPublication);
    }

    public List<ActualiteAcademie> getActualiteAcademiesByDateRange(LocalDate startDate, LocalDate endDate) {
        return actualiteAcademieRepository.findByDatePublicationBetween(startDate, endDate);
    }

    public List<ActualiteAcademie> searchActualiteAcademies(String query) {
        return actualiteAcademieRepository.findByTitreContainingOrContenuContaining(query, query);
    }

    // Update
    public ActualiteAcademie updateActualiteAcademie(Long id, ActualiteAcademie actualiteAcademie) {
        if (actualiteAcademieRepository.existsById(id)) {
            actualiteAcademie.setId(id);
            return actualiteAcademieRepository.save(actualiteAcademie);
        }
        throw new EntityNotFoundException("Actualité académie not found with id: " + id);
    }

    // Update status
    public ActualiteAcademie updateStatutPublication(Long id, StatutPublication statutPublication) {
        ActualiteAcademie actualiteAcademie = actualiteAcademieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actualité académie not found with id: " + id));
        
        actualiteAcademie.setStatutPublication(statutPublication);
        return actualiteAcademieRepository.save(actualiteAcademie);
    }

    // Delete
    public void deleteActualiteAcademie(Long id) {
        actualiteAcademieRepository.deleteById(id);
    }
} 