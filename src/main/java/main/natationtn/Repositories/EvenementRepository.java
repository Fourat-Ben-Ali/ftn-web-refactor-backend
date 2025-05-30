package main.natationtn.Repositories;
//hello
import main.natationtn.Entities.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {
}
