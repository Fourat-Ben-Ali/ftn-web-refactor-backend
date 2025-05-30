package main.natationtn.Services;

import jakarta.persistence.EntityNotFoundException;
import main.natationtn.Entities.ProgrammeFormation;
import main.natationtn.Entities.StatutPublication;
import main.natationtn.Repositories.ProgrammeFormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProgrammeFormationService {

    private final ProgrammeFormationRepository programmeFormationRepository;

    @Autowired
    public ProgrammeFormationService(ProgrammeFormationRepository programmeFormationRepository) {
        this.programmeFormationRepository = programmeFormationRepository;
    }

    // Create
    public ProgrammeFormation saveProgrammeFormation(ProgrammeFormation programmeFormation) {
        return programmeFormationRepository.save(programmeFormation);
    }

    // Read
    public List<ProgrammeFormation> getAllProgrammeFormations() {
        return programmeFormationRepository.findAll();
    }

    public Optional<ProgrammeFormation> getProgrammeFormationById(Long id) {
        return programmeFormationRepository.findById(id);
    }

    public List<ProgrammeFormation> getProgrammeFormationsByStatut(StatutPublication statutPublication) {
        return programmeFormationRepository.findByStatutPublication(statutPublication);
    }

    public List<ProgrammeFormation> getProgrammeFormationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return programmeFormationRepository.findByDateDebutBetween(startDate, endDate);
    }

    public List<ProgrammeFormation> searchProgrammeFormations(String query) {
        return programmeFormationRepository.findByTitreContaining(query);
    }

    // Update
    public ProgrammeFormation updateProgrammeFormation(Long id, ProgrammeFormation programmeFormation) {
        if (programmeFormationRepository.existsById(id)) {
            programmeFormation.setId(id);
            return programmeFormationRepository.save(programmeFormation);
        }
        throw new EntityNotFoundException("Programme de formation not found with id: " + id);
    }

    // Update status
    public ProgrammeFormation updateStatutPublication(Long id, StatutPublication statutPublication) {
        ProgrammeFormation programmeFormation = programmeFormationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Programme de formation not found with id: " + id));
        
        programmeFormation.setStatutPublication(statutPublication);
        return programmeFormationRepository.save(programmeFormation);
    }

    // Delete
    public void deleteProgrammeFormation(Long id) {
        programmeFormationRepository.deleteById(id);
    }
} 