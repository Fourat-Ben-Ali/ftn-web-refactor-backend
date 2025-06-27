package main.natationtn.Controllers;

import main.natationtn.Entities.OlympicSwimmingPool;
import main.natationtn.Services.OlympicSwimmingPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pools")
public class OlympicSwimmingPoolController {
    @Autowired
    private OlympicSwimmingPoolService poolService;

    @GetMapping
    public List<OlympicSwimmingPool> getAllPools() {
        return poolService.getAllPools();
    }

    @GetMapping("/{id}")
    public Optional<OlympicSwimmingPool> getPoolById(@PathVariable Long id) {
        return poolService.getPoolById(id);
    }

    @PostMapping
    public OlympicSwimmingPool createPool(@RequestBody OlympicSwimmingPool pool) {
        return poolService.savePool(pool);
    }

    @DeleteMapping("/{id}")
    public void deletePool(@PathVariable Long id) {
        poolService.deletePool(id);
    }
} 