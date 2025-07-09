package main.natationtn.Controllers;

import main.natationtn.Entities.Clubs;
import main.natationtn.Services.ClubsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubsController {
    @Autowired
    private ClubsService clubsService;


    @PostMapping("/import")
    public ResponseEntity<String> importClubs() {
        clubsService.importClubs();
        return ResponseEntity.ok("Clubs imported successfully.");
    }
    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllClubs() {
        clubsService.deleteAllClubs();
        return ResponseEntity.ok("All clubs have been deleted.");
    }

    @PostMapping
    public ResponseEntity<Clubs> createClub(@RequestBody Clubs club) {
        Clubs saved = clubsService.saveClub(club);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clubs> updateClub(@PathVariable Long id, @RequestBody Clubs club) {
        club.setId(id);
        Clubs updated = clubsService.saveClub(club);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubsService.deleteClub(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clubs> getClubById(@PathVariable Long id) {
        Clubs club = clubsService.getClubById(id);
        if (club != null) {
            return ResponseEntity.ok(club);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Clubs>> getAllClubs() {
        List<Clubs> clubs = clubsService.getAllClubs();
        return ResponseEntity.ok(clubs);
    }

    @GetMapping("/all-paginated")
    public Page<Clubs> getAllClubs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return clubsService.getAllClubs(page, size);
    }



}
