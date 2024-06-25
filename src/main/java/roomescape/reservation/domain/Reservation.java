package roomescape.reservation.domain;

import lombok.Data;
import roomescape.time.domain.Time;

@Data
public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation() {
    }

    public Reservation(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
