package main.natationtn.Repositories;

import main.natationtn.Entities.Clubs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClubRepository extends JpaRepository<Clubs, Long> {
    boolean existsByClubName(String clubName);
    Page<Clubs> findAll(Pageable pageable);
    Optional <Clubs> findByClubName( String clubName);

}
