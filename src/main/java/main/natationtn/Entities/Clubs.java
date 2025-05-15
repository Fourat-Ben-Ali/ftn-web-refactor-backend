package main.natationtn.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "club")
public class Clubs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String clubName;

    @Column
    private String description;

    public Clubs() {}

    public Clubs(long id, String clubName, String description) {
        this.id = id;
        this.clubName = clubName;
        this.description = description;
    }

    public Clubs( String clubName) {
        this.clubName = clubName;
        this.description = "Ajouter une description";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
