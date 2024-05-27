package roomescape.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Reservation {
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String date;

    private Time time;

    public Reservation(){
    }

    public Reservation(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
