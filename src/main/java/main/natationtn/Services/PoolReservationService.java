package main.natationtn.Services;

import main.natationtn.Entities.PoolReservation;
import main.natationtn.Entities.OlympicSwimmingPool;
import main.natationtn.Entities.Clubs;
import main.natationtn.Repositories.PoolReservationRepository;
import main.natationtn.Repositories.ClubRepository;
import main.natationtn.Repositories.OlympicSwimmingPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PoolReservationService {
    @Autowired
    private PoolReservationRepository reservationRepository;
    
    @Autowired
    private ClubRepository clubRepository;
    
    @Autowired
    private OlympicSwimmingPoolRepository poolRepository;

    public List<PoolReservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<PoolReservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public PoolReservation saveReservation(PoolReservation reservation) {
        // Validate lane number (1-10)
        if (reservation.getLane() < 1 || reservation.getLane() > 10) {
            throw new RuntimeException("Lane number must be between 1 and 10.");
        }
        
        // Check for conflicts including lane
        List<PoolReservation> conflicts = reservationRepository.findConflictingReservations(
            reservation.getPool(), reservation.getDate(), reservation.getStartTime(), reservation.getEndTime(), reservation.getLane()
        );
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("This pool and lane is already reserved for the selected date and time slot.");
        }
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<PoolReservation> getReservationsByPoolAndDate(OlympicSwimmingPool pool, LocalDate date) {
        return reservationRepository.findByPoolAndDate(pool, date);
    }

    // Simplified filtering method using stream operations
    public List<PoolReservation> filterReservations(String coach, Long clubId, Long poolId, LocalDate date, Integer lane) {
        // Start with all reservations
        List<PoolReservation> allReservations = getAllReservations();
        
        // Apply filters using stream operations
        return allReservations.stream()
            .filter(reservation -> coach == null || coach.trim().isEmpty() || 
                    (reservation.getCoach() != null && reservation.getCoach().toLowerCase().contains(coach.toLowerCase())))
            .filter(reservation -> clubId == null || 
                    (reservation.getClub() != null && reservation.getClub().getId() == clubId))
            .filter(reservation -> poolId == null || 
                    (reservation.getPool() != null && reservation.getPool().getId() == poolId))
            .filter(reservation -> date == null || 
                    (reservation.getDate() != null && reservation.getDate().equals(date)))
            .filter(reservation -> lane == null || 
                    (reservation.getLane() != null && reservation.getLane().equals(lane)))
            .collect(Collectors.toList());
    }
} 