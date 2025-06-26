package main.natationtn.Repositories;

import main.natationtn.Entities.PoolReservation;
import main.natationtn.Entities.OlympicSwimmingPool;
import main.natationtn.Entities.Clubs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PoolReservationRepository extends JpaRepository<PoolReservation, Long> {
    @Query("SELECT r FROM PoolReservation r WHERE r.pool = :pool AND r.date = :date AND ((r.startTime < :endTime AND r.endTime > :startTime))")
    List<PoolReservation> findConflictingReservations(@Param("pool") OlympicSwimmingPool pool, @Param("date") LocalDate date, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
    
    // Filter by coach name (case-insensitive)
    List<PoolReservation> findByCoachContainingIgnoreCase(String coach);
    
    // Filter by club
    List<PoolReservation> findByClub(Clubs club);
    
    // Filter by pool
    List<PoolReservation> findByPool(OlympicSwimmingPool pool);
    
    // Filter by date
    List<PoolReservation> findByDate(LocalDate date);
    
    // Filter by date range
    List<PoolReservation> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Filter by coach and club
    List<PoolReservation> findByCoachContainingIgnoreCaseAndClub(String coach, Clubs club);
    
    // Filter by coach and pool
    List<PoolReservation> findByCoachContainingIgnoreCaseAndPool(String coach, OlympicSwimmingPool pool);
    
    // Filter by coach and date
    List<PoolReservation> findByCoachContainingIgnoreCaseAndDate(String coach, LocalDate date);
    
    // Filter by club and pool
    List<PoolReservation> findByClubAndPool(Clubs club, OlympicSwimmingPool pool);
    
    // Filter by club and date
    List<PoolReservation> findByClubAndDate(Clubs club, LocalDate date);
    
    // Filter by pool and date
    List<PoolReservation> findByPoolAndDate(OlympicSwimmingPool pool, LocalDate date);
    
    // Filter by coach, club, and pool
    List<PoolReservation> findByCoachContainingIgnoreCaseAndClubAndPool(String coach, Clubs club, OlympicSwimmingPool pool);
    
    // Filter by coach, club, and date
    List<PoolReservation> findByCoachContainingIgnoreCaseAndClubAndDate(String coach, Clubs club, LocalDate date);
    
    // Filter by coach, pool, and date
    List<PoolReservation> findByCoachContainingIgnoreCaseAndPoolAndDate(String coach, OlympicSwimmingPool pool, LocalDate date);
    
    // Filter by club, pool, and date
    List<PoolReservation> findByClubAndPoolAndDate(Clubs club, OlympicSwimmingPool pool, LocalDate date);
    
    // Filter by all criteria
    List<PoolReservation> findByCoachContainingIgnoreCaseAndClubAndPoolAndDate(String coach, Clubs club, OlympicSwimmingPool pool, LocalDate date);
} 