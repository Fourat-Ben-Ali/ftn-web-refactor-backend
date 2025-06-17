package main.natationtn.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "licences")
public class Licence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // We'll use simple foreign key references instead of bidirectional relationships
    @Column(name = "athlete_id")
    private Long athleteId;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "valide_depuis")
    private LocalDate valideDepuis;

    @Column(name = "valide_jusqu_a")
    private LocalDate valideJusquA;

    // Default constructor
    public Licence() {
    }

    // Constructor with fields
    public Licence(Long athleteId, Long clubId, LocalDate valideDepuis, LocalDate valideJusquA) {
        this.athleteId = athleteId;
        this.clubId = clubId;
        this.valideDepuis = valideDepuis;
        this.valideJusquA = valideJusquA;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(Long athleteId) {
        this.athleteId = athleteId;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public LocalDate getValideDepuis() {
        return valideDepuis;
    }

    public void setValideDepuis(LocalDate valideDepuis) {
        this.valideDepuis = valideDepuis;
    }

    public LocalDate getValideJusquA() {
        return valideJusquA;
    }

    public void setValideJusquA(LocalDate valideJusquA) {
        this.valideJusquA = valideJusquA;
    }

    @Override
    public String toString() {
        return "Licence{" +
                "id=" + id +
                ", athleteId=" + athleteId +
                ", clubId=" + clubId +
                ", valideDepuis=" + valideDepuis +
                ", valideJusquA=" + valideJusquA +
                '}';
    }
}