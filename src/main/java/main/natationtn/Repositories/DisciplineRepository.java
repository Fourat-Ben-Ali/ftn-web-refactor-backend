package main.natationtn.Repositories;

import main.natationtn.Entities.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    Optional<Discipline> findByNom(String nom);
    List<Discipline> findByNomContaining(String nom);
}