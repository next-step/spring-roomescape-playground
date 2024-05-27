package roomescape.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Reservation {
    int id;

    @NotBlank
    String name;

    @NotBlank
    String date;

    @NotBlank
    String time;

    public Reservation(){
    }

    public Reservation(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
