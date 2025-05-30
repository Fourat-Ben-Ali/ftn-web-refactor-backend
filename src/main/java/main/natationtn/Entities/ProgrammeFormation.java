package main.natationtn.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "programme_formations")
public class ProgrammeFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_publication")
    private StatutPublication statutPublication;

    // Constructors
    public ProgrammeFormation() {
    }

    public ProgrammeFormation(String titre, String description, LocalDate dateDebut, LocalDate dateFin, StatutPublication statutPublication) {
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statutPublication = statutPublication;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public StatutPublication getStatutPublication() {
        return statutPublication;
    }

    public void setStatutPublication(StatutPublication statutPublication) {
        this.statutPublication = statutPublication;
    }

    @Override
    public String toString() {
        return "ProgrammeFormation{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", statutPublication=" + statutPublication +
                '}';
    }
} 