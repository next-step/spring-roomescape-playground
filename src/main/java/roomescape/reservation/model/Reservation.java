package roomescape.reservation.model;

import lombok.Getter;
import lombok.Setter;
import roomescape.time.model.Time;

import java.time.LocalDate;

@Getter
public class Reservation {

    @Setter
    private Long id;
    private String name;
    private LocalDate date;
    @Setter
    private Time time;

    public Reservation(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}