package main.natationtn.Controllers;

import main.natationtn.Entities.Discipline;
import main.natationtn.Services.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;

    @Autowired
    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Discipline> createDiscipline(@RequestBody Discipline discipline) {
        Discipline savedDiscipline = disciplineService.saveDiscipline(discipline);
        return new ResponseEntity<>(savedDiscipline, HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public ResponseEntity<List<Discipline>> getAllDisciplines() {
        List<Discipline> disciplines = disciplineService.getAllDisciplines();
        return new ResponseEntity<>(disciplines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discipline> getDisciplineById(@PathVariable Long id) {
        return disciplineService.getDisciplineById(id)
                .map(discipline -> new ResponseEntity<>(discipline, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<Discipline> getDisciplineByNom(@PathVariable String nom) {
        return disciplineService.getDisciplineByNom(nom)
                .map(discipline -> new ResponseEntity<>(discipline, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Discipline>> searchDisciplines(@RequestParam String query) {
        List<Discipline> disciplines = disciplineService.searchDisciplines(query);
        return new ResponseEntity<>(disciplines, HttpStatus.OK);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Discipline> updateDiscipline(@PathVariable Long id, @RequestBody Discipline discipline) {
        return disciplineService.getDisciplineById(id)
                .map(existingDiscipline -> {
                    discipline.setId(id);
                    Discipline updatedDiscipline = disciplineService.updateDiscipline(discipline);
                    return new ResponseEntity<>(updatedDiscipline, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable Long id) {
        return disciplineService.getDisciplineById(id)
                .map(discipline -> {
                    disciplineService.deleteDiscipline(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}