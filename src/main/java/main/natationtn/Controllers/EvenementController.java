package main.natationtn.Controllers;

import main.natationtn.Entities.Evenement;
import main.natationtn.Services.EvenementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evenements")
@CrossOrigin(origins = "http://localhost:4200")
public class EvenementController {

    private final EvenementService evenementService;

    @Autowired
    public EvenementController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    // ✅ Create
    @PostMapping
    public ResponseEntity<Evenement> createEvenement(@RequestBody Evenement evenement) {
        Evenement savedEvenement = evenementService.saveEvenement(evenement);
        return new ResponseEntity<>(savedEvenement, HttpStatus.CREATED);
    }


    // ✅ Read All
    @GetMapping
    public List<Evenement> getAllEvenements() {
        return evenementService.getAllEvenements();
    }

    // ✅ Read All (Paged)
    @GetMapping("/paged")
    public Page<Evenement> getEvenementsPaged(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        return evenementService.getAllEvenementsPaged(page, size);
    }

    // ✅ Read by ID
    @GetMapping("/{id}")
    public Optional<Evenement> getEvenementById(@PathVariable Long id) {
        return evenementService.getEvenementById(id);
    }

    // ✅ Update
    @PutMapping
    public Evenement updateEvenement(@RequestBody Evenement evenement) {
        return evenementService.updateEvenement(evenement);
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public void deleteEvenement(@PathVariable Long id) {
        evenementService.deleteEvenement(id);
    }
}
