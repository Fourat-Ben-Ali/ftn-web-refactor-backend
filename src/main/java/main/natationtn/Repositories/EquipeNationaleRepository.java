package main.natationtn.Repositories;

import main.natationtn.Entities.EquipeNationale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeNationaleRepository extends JpaRepository<EquipeNationale, Long> {
    // Keep the existing method for finding by discipline
    List<EquipeNationale> findByDisciplineId(Long disciplineId);

    // Remove the method findByMembresId as it's no longer a ManyToMany relationship
    // You would need to use the AthleteRepository to find an athlete's equipe
}