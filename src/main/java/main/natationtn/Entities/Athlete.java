package main.natationtn.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "athletes")
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom")
    private String nom;

    @Column(name = "genre")
    private String genre;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "nationalite")
    private String nationalite;

    // Relationship with Club (unchanged)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Clubs club;

    // Add OneToMany relationship with EquipeNationale
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_nationale_id")
    private EquipeNationale equipeNationale;

    // Constructors, getters, and setters
    public Athlete() {
    }

    public Athlete(String prenom, String nom, String genre, LocalDate dateNaissance, String nationalite) {
        this.prenom = prenom;
        this.nom = nom;
        this.genre = genre;
        this.dateNaissance = dateNaissance;
        this.nationalite = nationalite;
    }

    // Existing getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public Clubs getClub() {
        return club;
    }

    public void setClub(Clubs club) {
        this.club = club;
    }

    // Add getter and setter for equipeNationale
    public EquipeNationale getEquipeNationale() {
        return equipeNationale;
    }

    public void setEquipeNationale(EquipeNationale equipeNationale) {
        this.equipeNationale = equipeNationale;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", genre='" + genre + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", nationalite='" + nationalite + '\'' +
                ", club=" + (club != null ? club.getClubName() : null) +
                ", equipeNationale=" + (equipeNationale != null ? equipeNationale.getNom() : null) +
                '}';
    }
}