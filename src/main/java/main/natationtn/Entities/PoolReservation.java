package main.natationtn.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pool_reservations")
public class PoolReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String coach;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "club_id", nullable = false)
    private Clubs club;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pool_id", nullable = false)
    private OlympicSwimmingPool pool;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public PoolReservation() {}

    public PoolReservation(String coach, Clubs club, OlympicSwimmingPool pool, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.coach = coach;
        this.club = club;
        this.pool = pool;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCoach() { return coach; }
    public void setCoach(String coach) { this.coach = coach; }

    public Clubs getClub() { return club; }
    public void setClub(Clubs club) { this.club = club; }

    public OlympicSwimmingPool getPool() { return pool; }
    public void setPool(OlympicSwimmingPool pool) { this.pool = pool; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
} 