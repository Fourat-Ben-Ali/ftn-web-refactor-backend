package main.natationtn.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import main.natationtn.Entities.CalendarResponse;
import main.natationtn.Entities.Clubs;
import main.natationtn.Services.ScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
@Tag(name = "Scraping", description = "API for retrieving data scraped from the official FTN website")

public class ScrappingController {
    @Autowired
    private ScrapingService scrapingService;


    @Operation(
            summary = "Get the list of swimming clubs",
            description = "Scrapes and returns the list of swimming clubs from the official FTN website"
    )
    @GetMapping("/clubs")
    public List<Clubs> getClubs() throws IOException {
        return scrapingService.getClubs();
    }
    @Operation(
            summary = "Get raw international calendar data",
            description = "Returns the raw text extracted from the 2025 international swimming calendar PDF"
    )
    @GetMapping("/calendar/raw")
    public String scrapeCalendrierInternational() {
        return scrapingService.scrapeCalendrierInternational();
    }

    @Operation(
            summary = "Get structured international calendar data",
            description = "Parses the 2025 international swimming calendar PDF and returns structured JSON data"
    )
    @GetMapping("/calendar")
    public ResponseEntity<CalendarResponse> getCalendar() {
        try {
            CalendarResponse response = scrapingService.parseCalendar();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
