package main.natationtn.Controllers;

import main.natationtn.Entities.ProgrammeFormation;
import main.natationtn.Entities.StatutPublication;
import main.natationtn.Services.ProgrammeFormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programme-formations")
public class ProgrammeFormationController {

    private final ProgrammeFormationService programmeFormationService;

    @Autowired
    public ProgrammeFormationController(ProgrammeFormationService programmeFormationService) {
        this.programmeFormationService = programmeFormationService;
    }

    // Create
    @PostMapping
    public ResponseEntity<ProgrammeFormation> createProgrammeFormation(@RequestBody ProgrammeFormation programmeFormation) {
        ProgrammeFormation savedProgramme = programmeFormationService.saveProgrammeFormation(programmeFormation);
        return new ResponseEntity<>(savedProgramme, HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public ResponseEntity<List<ProgrammeFormation>> getAllProgrammeFormations() {
        List<ProgrammeFormation> programmes = programmeFormationService.getAllProgrammeFormations();
        return new ResponseEntity<>(programmes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgrammeFormation> getProgrammeFormationById(@PathVariable Long id) {
        return programmeFormationService.getProgrammeFormationById(id)
                .map(programme -> new ResponseEntity<>(programme, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ProgrammeFormation>> getProgrammeFormationsByStatut(
            @PathVariable StatutPublication statut) {
        List<ProgrammeFormation> programmes = programmeFormationService.getProgrammeFormationsByStatut(statut);
        return new ResponseEntity<>(programmes, HttpStatus.OK);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ProgrammeFormation>> getProgrammeFormationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ProgrammeFormation> programmes = programmeFormationService.getProgrammeFormationsByDateRange(startDate, endDate);
        return new ResponseEntity<>(programmes, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProgrammeFormation>> searchProgrammeFormations(@RequestParam String query) {
        List<ProgrammeFormation> programmes = programmeFormationService.searchProgrammeFormations(query);
        return new ResponseEntity<>(programmes, HttpStatus.OK);
    }

    @GetMapping("/statuts")
    public ResponseEntity<StatutPublication[]> getAllStatuts() {
        return new ResponseEntity<>(StatutPublication.values(), HttpStatus.OK);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ProgrammeFormation> updateProgrammeFormation(
            @PathVariable Long id,
            @RequestBody ProgrammeFormation programmeFormation) {
        try {
            ProgrammeFormation updatedProgramme = programmeFormationService.updateProgrammeFormation(id, programmeFormation);
            return new ResponseEntity<>(updatedProgramme, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<ProgrammeFormation> updateStatutPublication(
            @PathVariable Long id,
            @RequestBody Map<String, StatutPublication> request) {
        try {
            StatutPublication statutPublication = request.get("statutPublication");
            if (statutPublication == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            ProgrammeFormation updatedProgramme = programmeFormationService.updateStatutPublication(id, statutPublication);
            return new ResponseEntity<>(updatedProgramme, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgrammeFormation(@PathVariable Long id) {
        try {
            programmeFormationService.deleteProgrammeFormation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 