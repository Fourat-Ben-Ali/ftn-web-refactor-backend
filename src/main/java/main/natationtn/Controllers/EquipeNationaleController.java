package main.natationtn.Controllers;

import main.natationtn.Entities.EquipeNationale;
import main.natationtn.Services.EquipeNationaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipes-nationales")
public class EquipeNationaleController {

    private final EquipeNationaleService equipeNationaleService;

    @Autowired
    public EquipeNationaleController(EquipeNationaleService equipeNationaleService) {
        this.equipeNationaleService = equipeNationaleService;
    }

    // Create
    @PostMapping
    public ResponseEntity<EquipeNationale> createEquipeNationale(@RequestBody EquipeNationale equipeNationale) {
        EquipeNationale savedEquipe = equipeNationaleService.saveEquipeNationale(equipeNationale);
        return new ResponseEntity<>(savedEquipe, HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public ResponseEntity<List<EquipeNationale>> getAllEquipesNationales() {
        List<EquipeNationale> equipes = equipeNationaleService.getAllEquipesNationales();
        return new ResponseEntity<>(equipes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeNationale> getEquipeNationaleById(@PathVariable Long id) {
        return equipeNationaleService.getEquipeNationaleById(id)
                .map(equipe -> new ResponseEntity<>(equipe, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/discipline/{disciplineId}")
    public ResponseEntity<List<EquipeNationale>> getEquipesByDisciplineId(@PathVariable Long disciplineId) {
        List<EquipeNationale> equipes = equipeNationaleService.getEquipesByDisciplineId(disciplineId);
        return new ResponseEntity<>(equipes, HttpStatus.OK);
    }

    // Change from get multiple equipes to get a single equipe for an athlete
    @GetMapping("/athlete/{athleteId}")
    public ResponseEntity<EquipeNationale> getEquipeByAthleteId(@PathVariable Long athleteId) {
        try {
            EquipeNationale equipe = equipeNationaleService.getEquipeByAthleteId(athleteId);
            if (equipe != null) {
                return new ResponseEntity<>(equipe, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<EquipeNationale> updateEquipeNationale(@PathVariable Long id, @RequestBody EquipeNationale equipeNationale) {
        return equipeNationaleService.getEquipeNationaleById(id)
                .map(existingEquipe -> {
                    equipeNationale.setId(id);
                    EquipeNationale updatedEquipe = equipeNationaleService.updateEquipeNationale(equipeNationale);
                    return new ResponseEntity<>(updatedEquipe, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Keep these operations for managing athletes in the equipe
    @PostMapping("/{equipeId}/athletes/{athleteId}")
    public ResponseEntity<EquipeNationale> addAthleteToEquipe(@PathVariable Long equipeId, @PathVariable Long athleteId) {
        try {
            EquipeNationale updatedEquipe = equipeNationaleService.addAthleteToEquipe(equipeId, athleteId);
            return new ResponseEntity<>(updatedEquipe, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{equipeId}/athletes/{athleteId}")
    public ResponseEntity<EquipeNationale> removeAthleteFromEquipe(@PathVariable Long equipeId, @PathVariable Long athleteId) {
        try {
            EquipeNationale updatedEquipe = equipeNationaleService.removeAthleteFromEquipe(equipeId, athleteId);
            return new ResponseEntity<>(updatedEquipe, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipeNationale(@PathVariable Long id) {
        return equipeNationaleService.getEquipeNationaleById(id)
                .map(equipe -> {
                    equipeNationaleService.deleteEquipeNationale(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}