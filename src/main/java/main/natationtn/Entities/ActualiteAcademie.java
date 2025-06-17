package main.natationtn.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "actualite_academies")
public class ActualiteAcademie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "contenu")
    private String contenu;

    @Column(name = "date_publication")
    private LocalDate datePublication;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_publication")
    private StatutPublication statutPublication;

    // Constructors
    public ActualiteAcademie() {
    }

    public ActualiteAcademie(String titre, String contenu, LocalDate datePublication, StatutPublication statutPublication) {
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public StatutPublication getStatutPublication() {
        return statutPublication;
    }

    public void setStatutPublication(StatutPublication statutPublication) {
        this.statutPublication = statutPublication;
    }

    @Override
    public String toString() {
        return "ActualiteAcademie{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", datePublication=" + datePublication +
                ", statutPublication=" + statutPublication +
                '}';
    }
} 