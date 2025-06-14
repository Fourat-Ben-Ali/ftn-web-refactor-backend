package main.natationtn.Repositories;

import main.natationtn.Entities.ProgrammeFormation;
import main.natationtn.Entities.StatutPublication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProgrammeFormationRepository extends JpaRepository<ProgrammeFormation, Long> {
    List<ProgrammeFormation> findByStatutPublication(StatutPublication statutPublication);
    List<ProgrammeFormation> findByDateDebutBetween(LocalDate startDate, LocalDate endDate);
    List<ProgrammeFormation> findByTitreContaining(String titre);
} 