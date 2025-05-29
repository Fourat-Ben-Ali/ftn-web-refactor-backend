package main.natationtn.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titre;
    private LocalDate date;
    private String description;
    @Enumerated(EnumType.STRING)
    private TypeEvenement typeEvenement;

   @ManyToOne
   @JoinColumn(name = "discipline_id")
   private Discipline discipline;

    public void setId(Long id) {
        this.id = id;
    }

}