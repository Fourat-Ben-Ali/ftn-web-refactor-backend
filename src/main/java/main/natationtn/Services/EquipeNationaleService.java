package main.natationtn.Services;

import jakarta.persistence.EntityNotFoundException;
import main.natationtn.Entities.Athlete;
import main.natationtn.Entities.EquipeNationale;
import main.natationtn.Repositories.AthleteRepository;
import main.natationtn.Repositories.EquipeNationaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeNationaleService {

    private final EquipeNationaleRepository equipeNationaleRepository;
    private final AthleteRepository athleteRepository;

    @Autowired
    public EquipeNationaleService(EquipeNationaleRepository equipeNationaleRepository, AthleteRepository athleteRepository) {
        this.equipeNationaleRepository = equipeNationaleRepository;
        this.athleteRepository = athleteRepository;
    }

    // Create
    public EquipeNationale saveEquipeNationale(EquipeNationale equipeNationale) {
        return equipeNationaleRepository.save(equipeNationale);
    }

    // Read
    public List<EquipeNationale> getAllEquipesNationales() {
        return equipeNationaleRepository.findAll();
    }

    public Optional<EquipeNationale> getEquipeNationaleById(Long id) {
        return equipeNationaleRepository.findById(id);
    }

    public List<EquipeNationale> getEquipesByDisciplineId(Long disciplineId) {
        return equipeNationaleRepository.findByDisciplineId(disciplineId);
    }

    // New method to get a single athlete's equipe
    public EquipeNationale getEquipeByAthleteId(Long athleteId) {
        Athlete athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));
        return athlete.getEquipeNationale();
    }

    // Update
    public EquipeNationale updateEquipeNationale(EquipeNationale equipeNationale) {
        return equipeNationaleRepository.save(equipeNationale);
    }

    // Special operations for OneToMany relationship
    @Transactional
    public EquipeNationale addAthleteToEquipe(Long equipeId, Long athleteId) {
        EquipeNationale equipe = equipeNationaleRepository.findById(equipeId)
                .orElseThrow(() -> new EntityNotFoundException("Equipe not found"));

        Athlete athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));

        // Check if athlete already belongs to an equipe
        if (athlete.getEquipeNationale() != null) {
            // If athlete is already in this equipe, do nothing
            if (athlete.getEquipeNationale().getId().equals(equipeId)) {
                return equipe;
            }
            // If athlete is in another equipe, remove from that one first
            athlete.getEquipeNationale().getMembres().remove(athlete);
        }

        // Add athlete to this equipe
        equipe.addAthlete(athlete);
        equipeNationaleRepository.save(equipe);

        return equipe;
    }

    @Transactional
    public EquipeNationale removeAthleteFromEquipe(Long equipeId, Long athleteId) {
        EquipeNationale equipe = equipeNationaleRepository.findById(equipeId)
                .orElseThrow(() -> new EntityNotFoundException("Equipe not found"));

        Athlete athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));

        // Only remove if athlete belongs to this equipe
        if (athlete.getEquipeNationale() != null &&
                athlete.getEquipeNationale().getId().equals(equipeId)) {
            equipe.removeAthlete(athlete);
            equipeNationaleRepository.save(equipe);
        }

        return equipe;
    }

    // Delete
    public void deleteEquipeNationale(Long id) {
        // Get equipe
        EquipeNationale equipe = equipeNationaleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipe not found"));

        // Remove the association from all athletes
        for (Athlete athlete : equipe.getMembres()) {
            athlete.setEquipeNationale(null);
        }

        // Clear the membres list
        equipe.getMembres().clear();

        // Now delete the equipe
        equipeNationaleRepository.deleteById(id);
    }
}