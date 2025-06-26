package main.natationtn.Services;

import main.natationtn.Entities.OlympicSwimmingPool;
import main.natationtn.Repositories.OlympicSwimmingPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OlympicSwimmingPoolService {
    @Autowired
    private OlympicSwimmingPoolRepository poolRepository;

    public List<OlympicSwimmingPool> getAllPools() {
        return poolRepository.findAll();
    }

    public Optional<OlympicSwimmingPool> getPoolById(Long id) {
        return poolRepository.findById(id);
    }

    public OlympicSwimmingPool savePool(OlympicSwimmingPool pool) {
        return poolRepository.save(pool);
    }

    public void deletePool(Long id) {
        poolRepository.deleteById(id);
    }
} 