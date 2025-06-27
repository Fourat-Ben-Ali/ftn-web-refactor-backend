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
        // Check for conflicts
        List<PoolReservation> conflicts = reservationRepository.findConflictingReservations(
            reservation.getPool(), reservation.getDate(), reservation.getStartTime(), reservation.getEndTime()
        );
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("This pool is already reserved for the selected date and time slot.");
        }
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<PoolReservation> getReservationsByPoolAndDate(OlympicSwimmingPool pool, LocalDate date) {
        return reservationRepository.findConflictingReservations(pool, date, LocalTime.MIN, LocalTime.MAX);
    }

    // Filtering methods
    public List<PoolReservation> filterReservations(String coach, Long clubId, Long poolId, LocalDate date) {
        // If no filters are provided, return all reservations
        if ((coach == null || coach.trim().isEmpty()) && clubId == null && poolId == null && date == null) {
            return getAllReservations();
        }

        // Build filter criteria
        String coachFilter = (coach != null && !coach.trim().isEmpty()) ? coach.trim() : null;
        Clubs clubFilter = null;
        OlympicSwimmingPool poolFilter = null;

        // Fetch club and pool entities if IDs are provided
        if (clubId != null) {
            Optional<Clubs> clubOpt = clubRepository.findById(clubId);
            if (clubOpt.isPresent()) {
                clubFilter = clubOpt.get();
            }
        }
        
        if (poolId != null) {
            Optional<OlympicSwimmingPool> poolOpt = poolRepository.findById(poolId);
            if (poolOpt.isPresent()) {
                poolFilter = poolOpt.get();
            }
        }
        
        // Apply filters based on what's provided
        if (coachFilter != null && clubFilter != null && poolFilter != null && date != null) {
            // All filters provided
            return reservationRepository.findByCoachContainingIgnoreCaseAndClubAndPoolAndDate(
                coachFilter, clubFilter, poolFilter, date);
        } else if (coachFilter != null && clubFilter != null && poolFilter != null) {
            // Coach, club, and pool filters
            return reservationRepository.findByCoachContainingIgnoreCaseAndClubAndPool(
                coachFilter, clubFilter, poolFilter);
        } else if (coachFilter != null && clubFilter != null && date != null) {
            // Coach, club, and date filters
            return reservationRepository.findByCoachContainingIgnoreCaseAndClubAndDate(
                coachFilter, clubFilter, date);
        } else if (coachFilter != null && poolFilter != null && date != null) {
            // Coach, pool, and date filters
            return reservationRepository.findByCoachContainingIgnoreCaseAndPoolAndDate(
                coachFilter, poolFilter, date);
        } else if (clubFilter != null && poolFilter != null && date != null) {
            // Club, pool, and date filters
            return reservationRepository.findByClubAndPoolAndDate(clubFilter, poolFilter, date);
        } else if (coachFilter != null && clubFilter != null) {
            // Coach and club filters
            return reservationRepository.findByCoachContainingIgnoreCaseAndClub(coachFilter, clubFilter);
        } else if (coachFilter != null && poolFilter != null) {
            // Coach and pool filters
            return reservationRepository.findByCoachContainingIgnoreCaseAndPool(coachFilter, poolFilter);
        } else if (coachFilter != null && date != null) {
            // Coach and date filters
            return reservationRepository.findByCoachContainingIgnoreCaseAndDate(coachFilter, date);
        } else if (clubFilter != null && poolFilter != null) {
            // Club and pool filters
            return reservationRepository.findByClubAndPool(clubFilter, poolFilter);
        } else if (clubFilter != null && date != null) {
            // Club and date filters
            return reservationRepository.findByClubAndDate(clubFilter, date);
        } else if (poolFilter != null && date != null) {
            // Pool and date filters
            return reservationRepository.findByPoolAndDate(poolFilter, date);
        } else if (coachFilter != null) {
            // Only coach filter
            return reservationRepository.findByCoachContainingIgnoreCase(coachFilter);
        } else if (clubFilter != null) {
            // Only club filter
            return reservationRepository.findByClub(clubFilter);
        } else if (poolFilter != null) {
            // Only pool filter
            return reservationRepository.findByPool(poolFilter);
        } else if (date != null) {
            // Only date filter
            return reservationRepository.findByDate(date);
        }

        return getAllReservations();
    }
} 