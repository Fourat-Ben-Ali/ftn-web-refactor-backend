package main.natationtn.Controllers;

import main.natationtn.Entities.Athlete;
import main.natationtn.Services.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/athletes")
public class AthleteController {

    private final AthleteService athleteService;

    @Autowired
    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Athlete> createAthlete(@RequestBody Athlete athlete) {
        Athlete savedAthlete = athleteService.saveAthlete(athlete);
        return new ResponseEntity<>(savedAthlete, HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public ResponseEntity<List<Athlete>> getAllAthletes() {
        List<Athlete> athletes = athleteService.getAllAthletes();
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Athlete> getAthleteById(@PathVariable Long id) {
        return athleteService.getAthleteById(id)
                .map(athlete -> new ResponseEntity<>(athlete, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<Athlete>> getAthletesByClub(@PathVariable Long clubId) {
        List<Athlete> athletes = athleteService.getAthletesByClub(clubId);
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    // Add endpoint to get athletes by equipeNationale
    @GetMapping("/equipe/{equipeId}")
    public ResponseEntity<List<Athlete>> getAthletesByEquipeNationale(@PathVariable Long equipeId) {
        List<Athlete> athletes = athleteService.getAthletesByEquipeNationale(equipeId);
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Athlete>> searchAthletes(@RequestParam String query) {
        List<Athlete> athletes = athleteService.searchAthletes(query);
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Athlete> updateAthlete(@PathVariable Long id, @RequestBody Athlete athlete) {
        return athleteService.getAthleteById(id)
                .map(existingAthlete -> {
                    athlete.setId(id);
                    Athlete updatedAthlete = athleteService.updateAthlete(athlete);
                    return new ResponseEntity<>(updatedAthlete, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Add endpoints to manage equipeNationale assignment
    @PutMapping("/{athleteId}/assign-equipe/{equipeId}")
    public ResponseEntity<Athlete> assignAthleteToEquipe(
            @PathVariable Long athleteId,
            @PathVariable Long equipeId) {
        try {
            Athlete updatedAthlete = athleteService.assignAthleteToEquipe(athleteId, equipeId);
            return new ResponseEntity<>(updatedAthlete, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{athleteId}/remove-from-equipe")
    public ResponseEntity<Athlete> removeAthleteFromEquipe(@PathVariable Long athleteId) {
        try {
            Athlete updatedAthlete = athleteService.removeAthleteFromEquipe(athleteId);
            return new ResponseEntity<>(updatedAthlete, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        return athleteService.getAthleteById(id)
                .map(athlete -> {
                    athleteService.deleteAthlete(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}