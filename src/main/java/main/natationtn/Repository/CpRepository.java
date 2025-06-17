package main.natationtn.Repository;

import main.natationtn.Entities.ContenuPresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpRepository extends JpaRepository< ContenuPresse,Long> {

}
