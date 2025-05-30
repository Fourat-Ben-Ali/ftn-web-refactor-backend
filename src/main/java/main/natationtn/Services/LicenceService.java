package main.natationtn.Services;

import main.natationtn.Entities.Licence;
import main.natationtn.Repositories.LicenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LicenceService {

    private final LicenceRepository licenceRepository;

    @Autowired
    public LicenceService(LicenceRepository licenceRepository) {
        this.licenceRepository = licenceRepository;
    }

    // Create
    public Licence saveLicence(Licence licence) {
        return licenceRepository.save(licence);
    }

    // Read
    public List<Licence> getAllLicences() {
        return licenceRepository.findAll();
    }

    public Optional<Licence> getLicenceById(Long id) {
        return licenceRepository.findById(id);
    }

    public List<Licence> getLicencesByAthleteId(Long athleteId) {
        return licenceRepository.findByAthleteId(athleteId);
    }

    public List<Licence> getLicencesByClubId(Long clubId) {
        return licenceRepository.findByClubId(clubId);
    }

    public List<Licence> getActiveLicences(LocalDate date) {
        return licenceRepository.findByValideDepuisBeforeAndValideJusquAAfter(date, date);
    }

    public List<Licence> getExpiredLicences(LocalDate date) {
        return licenceRepository.findByValideJusquAAfter(date);
    }

    public List<Licence> getFutureLicences(LocalDate date) {
        return licenceRepository.findByValideDepuisBefore(date);
    }

    // Update
    public Licence updateLicence(Licence licence) {
        return licenceRepository.save(licence);
    }

    // Delete
    public void deleteLicence(Long id) {
        licenceRepository.deleteById(id);
    }
}