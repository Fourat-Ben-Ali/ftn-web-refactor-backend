package main.natationtn.Repositories;

import main.natationtn.Entities.ActualiteAcademie;
import main.natationtn.Entities.StatutPublication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActualiteAcademieRepository extends JpaRepository<ActualiteAcademie, Long> {
    List<ActualiteAcademie> findByStatutPublication(StatutPublication statutPublication);
    List<ActualiteAcademie> findByDatePublicationBetween(LocalDate startDate, LocalDate endDate);
    List<ActualiteAcademie> findByTitreContainingOrContenuContaining(String titre, String contenu);
} 