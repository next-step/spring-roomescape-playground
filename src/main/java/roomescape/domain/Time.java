package roomescape.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message="Time cannot be empty")
    private String time;

    public Time(){}

    public Time(String id) {
        this.id = setId(Long.parseLong(id));
    }
    public long getId() {return this.id;}
    public String getTime() {return this.time;}

    public long setId(long id) {return this.id = id;}
    public String setTime(String time) {return this.time = time;}
}
