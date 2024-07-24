package roomescape.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
