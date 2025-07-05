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
    @Query("SELECT r FROM PoolReservation r WHERE r.pool = :pool AND r.date = :date AND r.lane = :lane AND ((r.startTime < :endTime AND r.endTime > :startTime))")
    List<PoolReservation> findConflictingReservations(@Param("pool") OlympicSwimmingPool pool, @Param("date") LocalDate date, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime, @Param("lane") Integer lane);
    
    // Basic filter methods (kept for potential direct use)
    List<PoolReservation> findByCoachContainingIgnoreCase(String coach);
    List<PoolReservation> findByClub(Clubs club);
    List<PoolReservation> findByPool(OlympicSwimmingPool pool);
    List<PoolReservation> findByDate(LocalDate date);
    List<PoolReservation> findByLane(Integer lane);
    List<PoolReservation> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<PoolReservation> findByPoolAndDate(OlympicSwimmingPool pool, LocalDate date);
} 