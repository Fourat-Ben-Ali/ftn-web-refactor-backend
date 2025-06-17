package main.natationtn.Controllers;

import main.natationtn.Entities.Licence;
import main.natationtn.Services.LicenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/licences")
public class LicenceController {

    private final LicenceService licenceService;

    @Autowired
    public LicenceController(LicenceService licenceService) {
        this.licenceService = licenceService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Licence> createLicence(@RequestBody Licence licence) {
        Licence savedLicence = licenceService.saveLicence(licence);
        return new ResponseEntity<>(savedLicence, HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public ResponseEntity<List<Licence>> getAllLicences() {
        List<Licence> licences = licenceService.getAllLicences();
        return new ResponseEntity<>(licences, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Licence> getLicenceById(@PathVariable Long id) {
        return licenceService.getLicenceById(id)
                .map(licence -> new ResponseEntity<>(licence, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/athlete/{athleteId}")
    public ResponseEntity<List<Licence>> getLicencesByAthleteId(@PathVariable Long athleteId) {
        List<Licence> licences = licenceService.getLicencesByAthleteId(athleteId);
        return new ResponseEntity<>(licences, HttpStatus.OK);
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<Licence>> getLicencesByClubId(@PathVariable Long clubId) {
        List<Licence> licences = licenceService.getLicencesByClubId(clubId);
        return new ResponseEntity<>(licences, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Licence>> getActiveLicences(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate checkDate = date != null ? date : LocalDate.now();
        List<Licence> licences = licenceService.getActiveLicences(checkDate);
        return new ResponseEntity<>(licences, HttpStatus.OK);
    }

    @GetMapping("/expired")
    public ResponseEntity<List<Licence>> getExpiredLicences(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate checkDate = date != null ? date : LocalDate.now();
        List<Licence> licences = licenceService.getExpiredLicences(checkDate);
        return new ResponseEntity<>(licences, HttpStatus.OK);
    }

    @GetMapping("/future")
    public ResponseEntity<List<Licence>> getFutureLicences(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate checkDate = date != null ? date : LocalDate.now();
        List<Licence> licences = licenceService.getFutureLicences(checkDate);
        return new ResponseEntity<>(licences, HttpStatus.OK);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Licence> updateLicence(@PathVariable Long id, @RequestBody Licence licence) {
        return licenceService.getLicenceById(id)
                .map(existingLicence -> {
                    licence.setId(id);
                    Licence updatedLicence = licenceService.updateLicence(licence);
                    return new ResponseEntity<>(updatedLicence, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicence(@PathVariable Long id) {
        return licenceService.getLicenceById(id)
                .map(licence -> {
                    licenceService.deleteLicence(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}