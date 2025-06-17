package main.natationtn.Repositories;

import main.natationtn.Entities.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    // Existing methods
    List<Athlete> findByClubId(Long clubId);
    List<Athlete> findByNomContainingOrPrenomContaining(String nom, String prenom);

    // New method for finding athletes by equipeNationale
    List<Athlete> findByEquipeNationaleId(Long equipeNationaleId);
}