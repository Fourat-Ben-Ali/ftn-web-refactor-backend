package main.natationtn.Controllers;

import main.natationtn.Entities.RaceResult;
import main.natationtn.Services.RaceScraperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class RaceScraperController {

    private final RaceScraperService raceScraperService;


    public RaceScraperController(RaceScraperService raceScraperService) {
        this.raceScraperService = raceScraperService;
    }

    @GetMapping("/api/scrape-events")
    public ResponseEntity<List<RaceResult>> scrapeEvent(@RequestParam String url) {
        try {
            List<RaceResult> results = raceScraperService.getEventDetails(url);
            return ResponseEntity.ok(results);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/api/scrape-blogs")
    public ResponseEntity<List<Map<String, String>>> scrapeBlogs() {
        List<Map<String, String>> results = raceScraperService.extractBlogsAndEnrichWithResultLinks();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/api/scrape-all")
    public ResponseEntity<List<RaceResult>> scrapeRaces() {
        List<RaceResult> results = raceScraperService.scrapeAllEventsFromBlogs();
        return ResponseEntity.ok(results);
    }
}
