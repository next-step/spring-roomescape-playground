package roomescape.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Time;

@Data
public class Reservation {
    int id;

    @NotBlank
    String name;

    @NotBlank
    String date;

    @NotBlank
    Time time;

    public Reservation(){
    }

    public Reservation(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
