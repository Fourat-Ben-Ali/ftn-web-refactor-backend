package main.natationtn.Controllers;

import main.natationtn.Entities.ContenuPresse;
import main.natationtn.Services.CpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contenus")
public class CpController {

    @Autowired
    private CpService cpService;

    // CREATE
    @PostMapping("/add")
    public ResponseEntity<ContenuPresse> addContenu(@RequestBody ContenuPresse cp) {
        System.out.println("DEBUG - Contenu reçu: " + cp);
        ContenuPresse saved = cpService.ADDContenu(cp);
        return ResponseEntity.ok(saved);
    }

    // READ - All
    @GetMapping("/ListContenu")
    public List<ContenuPresse> getAllContenus() {
    
        List<ContenuPresse> contenus = cpService.getAllContenus();
        return contenus;
    }

    // READ - By ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<ContenuPresse> getContenuById(@PathVariable Long id) {
        return cpService.getContenuById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/Update/{id}")
    public ResponseEntity<ContenuPresse> updateContenu(@PathVariable Long id, @RequestBody ContenuPresse cp) {
        try {
            ContenuPresse updated = cpService.updateContenu(id, cp);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteContenu(@PathVariable Long id) {
        cpService.deleteContenu(id);
        return ResponseEntity.noContent().build();
    }
}
