package main.natationtn.Repositories;

import main.natationtn.Entities.EquipeNationale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipeNationaleRepository extends JpaRepository<EquipeNationale, Long> {
    // Keep the existing method for finding by discipline
    List<EquipeNationale> findByDisciplineId(Long disciplineId);
    Optional <EquipeNationale> findByNom( String nom);

}