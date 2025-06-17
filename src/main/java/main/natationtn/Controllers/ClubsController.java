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
