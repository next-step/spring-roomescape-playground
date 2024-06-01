package roomescape.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message="Name cannot be empty")
    private String name;

    @NotBlank(message="Date cannot be empty")
    private String date;

    @ManyToOne
    private Time time;

    public Reservation(){}

    public long getId() {return this.id;}
    public String getName() {return this.name;}
    public String getDate() {return this.date;}
    public Time getTime() {return this.time;}

    public long setId(long id) {return this.id = id;}
    public String setName(String name) {return this.name = name;}
    public String setDate(String date) {return this.date = date;}
    public Time setTime(Time time) {return this.time = time;}
}
