package main.natationtn.Repositories;

import main.natationtn.Entities.Licence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LicenceRepository extends JpaRepository<Licence, Long> {
    List<Licence> findByAthleteId(Long athleteId);
    List<Licence> findByClubId(Long clubId);
    List<Licence> findByValideJusquAAfter(LocalDate date);
    List<Licence> findByValideDepuisBefore(LocalDate date);
    List<Licence> findByValideDepuisBeforeAndValideJusquAAfter(LocalDate startDate, LocalDate endDate);
}