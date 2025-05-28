package main.natationtn.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class ContenuPresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Enumerated(EnumType.STRING)
    private TypePresse type;


    private String contenu;

    private LocalDate datePublication;

    //private StatutPublication statutPublication;

    // Constructeurs
    public ContenuPresse() {}


}

