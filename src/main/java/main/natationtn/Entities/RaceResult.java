package main.natationtn.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "race_result")
@Getter
@Setter
public class RaceResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String startDate;
    private String endDate;
    private String race;
    private String gender;
    private String age;
    private String place;
    private String name;
    private String nation;
    private String birth;
    private String club;
    private String time;
    private String points;
    private String splitTime;
}
