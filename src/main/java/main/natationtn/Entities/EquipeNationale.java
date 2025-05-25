package main.natationtn.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipes_nationales")
public class EquipeNationale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @JsonIgnore
    @OneToMany(mappedBy = "equipeNationale", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Athlete> membres = new ArrayList<>();

    // Constructors
    public EquipeNationale() {
    }

    public EquipeNationale(String nom, Discipline discipline) {
        this.nom = nom;
        this.discipline = discipline;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    // Changed to return List instead of Set
    public List<Athlete> getMembres() {
        return membres;
    }

    public void setMembres(List<Athlete> membres) {
        this.membres = membres;
    }

    // Helper methods for managing the relationship
    public void addAthlete(Athlete athlete) {
        membres.add(athlete);
        athlete.setEquipeNationale(this);
    }

    public void removeAthlete(Athlete athlete) {
        membres.remove(athlete);
        athlete.setEquipeNationale(null);
    }

    @Override
    public String toString() {
        return "EquipeNationale{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", discipline=" + (discipline != null ? discipline.getNom() : null) +
                '}';
    }
}