package main.natationtn.Controllers;

import main.natationtn.Entities.PoolReservation;
import main.natationtn.Services.PoolReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pool-reservations")
public class PoolReservationController {
    @Autowired
    private PoolReservationService reservationService;

    @GetMapping
    public List<PoolReservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/filter")
    public List<PoolReservation> filterReservations(
            @RequestParam(required = false) String coach,
            @RequestParam(required = false) Long clubId,
            @RequestParam(required = false) Long poolId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return reservationService.filterReservations(coach, clubId, poolId, date);
    }

    @GetMapping("/{id}")
    public Optional<PoolReservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping
    public PoolReservation createReservation(@RequestBody PoolReservation reservation) {
        return reservationService.saveReservation(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("already reserved")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("message", ex.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(java.util.Collections.singletonMap("message", "An unexpected error occurred."));
    }
} 