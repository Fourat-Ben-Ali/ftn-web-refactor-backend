package main.natationtn.Repositories;

import main.natationtn.Entities.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    // Existing methods
    List<Athlete> findByClubId(Long clubId);
    List<Athlete> findByNomContainingOrPrenomContaining(String nom, String prenom);

    // New method for finding athletes by equipeNationale
    List<Athlete> findByEquipeNationaleId(Long equipeNationaleId);
    Optional <Athlete> findByPrenomAndNomAndDateNaissance( String prenom, String nom, LocalDate dateNaissance);

    boolean existsByNomAndPrenomAndDateNaissance(String nom, String prenom, LocalDate dateNaissance);

}