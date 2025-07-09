package main.natationtn.Services;

import main.natationtn.Entities.Clubs;
import main.natationtn.Repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class ClubsService {

    @Autowired
    private  ScrapingService scrapingService;
    @Autowired
    private  ClubRepository clubRepository;

    public void importClubs() {
        try {
            List<Clubs> scrapedClubs = scrapingService.getClubs();
            for (Clubs club : scrapedClubs) {
                if (!clubRepository.existsByClubName(club.getClubName())) {
                    clubRepository.save(club);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to scrape and save clubs", e);
        }
    }

    public void deleteAllClubs() {
        clubRepository.deleteAll();
    }
    public List<Clubs> getAllClubs() {
        return clubRepository.findAll();
    }
    public Page<Clubs> getAllClubs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clubRepository.findAll(pageable);
    }

    public Clubs saveClub(Clubs club) {
        return clubRepository.save(club);
    }
    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }
    public Clubs getClubById(Long id) {
        return clubRepository.findById(id).orElse(null);
    }
}
