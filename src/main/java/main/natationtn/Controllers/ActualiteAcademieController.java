package main.natationtn.Controllers;

import main.natationtn.Entities.ActualiteAcademie;
import main.natationtn.Entities.StatutPublication;
import main.natationtn.Services.ActualiteAcademieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/actualite-academies")

public class ActualiteAcademieController {

    private final ActualiteAcademieService actualiteAcademieService;

    @Autowired
    public ActualiteAcademieController(ActualiteAcademieService actualiteAcademieService) {
        this.actualiteAcademieService = actualiteAcademieService;
    }


//    @PostMapping
//    public ResponseEntity<ActualiteAcademie> createActualiteAcademie(@RequestBody ActualiteAcademie actualiteAcademie) {
//        ActualiteAcademie savedActualite = actualiteAcademieService.saveActualiteAcademie(actualiteAcademie);
//        return new ResponseEntity<>(savedActualite, HttpStatus.CREATED);
//    }
@PostMapping
public ResponseEntity<ActualiteAcademie> createActualiteAcademie(@RequestBody ActualiteAcademie actualite) {
    ActualiteAcademie createdActualite = actualiteAcademieService.createActualiteAcademie(actualite);
    return new ResponseEntity<>(createdActualite, HttpStatus.CREATED);
}
    // Read
    @GetMapping
    public ResponseEntity<List<ActualiteAcademie>> getAllActualiteAcademies() {
        List<ActualiteAcademie> actualites = actualiteAcademieService.getAllActualiteAcademies();
        return new ResponseEntity<>(actualites, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActualiteAcademie> getActualiteAcademieById(@PathVariable Long id) {
        return actualiteAcademieService.getActualiteAcademieById(id)
                .map(actualite -> new ResponseEntity<>(actualite, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
//
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ActualiteAcademie>> getActualiteAcademiesByStatut(
            @PathVariable StatutPublication statut) {
        List<ActualiteAcademie> actualites = actualiteAcademieService.getActualiteAcademiesByStatut(statut);
        return new ResponseEntity<>(actualites, HttpStatus.OK);
    }
//
    @GetMapping("/date-range")
    public ResponseEntity<List<ActualiteAcademie>> getActualiteAcademiesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ActualiteAcademie> actualites = actualiteAcademieService.getActualiteAcademiesByDateRange(startDate, endDate);
        return new ResponseEntity<>(actualites, HttpStatus.OK);
    }
//
    @GetMapping("/search")
    public ResponseEntity<List<ActualiteAcademie>> searchActualiteAcademies(@RequestParam String query) {
        List<ActualiteAcademie> actualites = actualiteAcademieService.searchActualiteAcademies(query);
        return new ResponseEntity<>(actualites, HttpStatus.OK);
    }
//
//     Update
    @PutMapping("/{id}")
    public ResponseEntity<ActualiteAcademie> updateActualiteAcademie(
            @PathVariable Long id,
            @RequestBody ActualiteAcademie actualiteAcademie) {
        try {
            ActualiteAcademie updatedActualite = actualiteAcademieService.updateActualiteAcademie(id, actualiteAcademie);
            return new ResponseEntity<>(updatedActualite, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//
    @PutMapping("/{id}/statut")
    public ResponseEntity<ActualiteAcademie> updateStatutPublication(
            @PathVariable Long id,
            @RequestBody StatutPublication statutPublication) {
        try {
            ActualiteAcademie updatedActualite = actualiteAcademieService.updateStatutPublication(id, statutPublication);
            return new ResponseEntity<>(updatedActualite, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//
//    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActualiteAcademie(@PathVariable Long id) {
        try {
            actualiteAcademieService.deleteActualiteAcademie(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}