package main.natationtn.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
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

    public ContenuPresse(String titre, TypePresse type, String contenu, LocalDate datePublication/*, StatutPublication statutPublication*/) {
        this.titre = titre;
        this.type = type;
        this.contenu = contenu;
        this.datePublication = datePublication;
       // this.statutPublication = statutPublication;
    }

}

