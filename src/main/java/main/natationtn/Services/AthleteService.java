package main.natationtn.Services;

import jakarta.persistence.EntityNotFoundException;
import main.natationtn.Entities.Athlete;

import main.natationtn.Entities.Clubs;
import main.natationtn.Entities.EquipeNationale;
import main.natationtn.Repositories.AthleteRepository;
import main.natationtn.Repositories.ClubRepository;
import main.natationtn.Repositories.EquipeNationaleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private ClubRepository clubRepository;
    private EquipeNationaleRepository equipeNationaleRepository; // Add this

    @Autowired
    public AthleteService(AthleteRepository athleteRepository,
                          ClubRepository clubRepository,
                          EquipeNationaleRepository equipeNationaleRepository) {
        this.athleteRepository = athleteRepository;
        this.clubRepository = clubRepository;
        this.equipeNationaleRepository = equipeNationaleRepository;
    }

    // Create
    public Athlete saveAthlete(Athlete athlete) {
        // If athlete has a club assigned, ensure the relationship is properly set
        if (athlete.getClub() != null) {
            // Don't check for getId() != null since it's a primitive long
            Clubs club = clubRepository.findById(athlete.getClub().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Club not found"));
            athlete.setClub(club);
        }

        // Similarly handle equipeNationale if it's set
        if (athlete.getEquipeNationale() != null && athlete.getEquipeNationale().getId() != null) {
            EquipeNationale equipe = equipeNationaleRepository.findById(athlete.getEquipeNationale().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Equipe Nationale not found"));
            athlete.setEquipeNationale(equipe);
        }

        return athleteRepository.save(athlete);
    }

    // Read
    public List<Athlete> getAllAthletes() {
        return athleteRepository.findAll();
    }

    public Optional<Athlete> getAthleteById(Long id) {
        return athleteRepository.findById(id);
    }

    public List<Athlete> getAthletesByClub(Long clubId) {
        return athleteRepository.findByClubId(clubId);
    }

    // Add method to get athletes by equipeNationale
    public List<Athlete> getAthletesByEquipeNationale(Long equipeId) {
        return athleteRepository.findByEquipeNationaleId(equipeId);
    }

    public List<Athlete> searchAthletes(String query) {
        return athleteRepository.findByNomContainingOrPrenomContaining(query, query);
    }

    // Update
    public Athlete updateAthlete(Athlete athlete) {
        return athleteRepository.save(athlete);
    }

    // Method to assign athlete to an equipeNationale
    public Athlete assignAthleteToEquipe(Long athleteId, Long equipeId) {
        Athlete athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));

        EquipeNationale equipe = equipeNationaleRepository.findById(equipeId)
                .orElseThrow(() -> new EntityNotFoundException("Equipe Nationale not found"));

        athlete.setEquipeNationale(equipe);
        return athleteRepository.save(athlete);
    }

    // Method to remove athlete from an equipeNationale
    public Athlete removeAthleteFromEquipe(Long athleteId) {
        Athlete athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));

        athlete.setEquipeNationale(null);
        return athleteRepository.save(athlete);
    }

    // Delete
    public void deleteAthlete(Long id) {
        athleteRepository.deleteById(id);
    }

    // Add this method to fetch an athlete with its club and equipeNationale
    public Athlete getAthleteWithRelationships(Long id) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));

        // Force initialization of the club if it exists
        if (athlete.getClub() != null) {
            Hibernate.initialize(athlete.getClub());
        }

        // Force initialization of the equipeNationale if it exists
        if (athlete.getEquipeNationale() != null) {
            Hibernate.initialize(athlete.getEquipeNationale());
        }

        return athlete;
    }
}