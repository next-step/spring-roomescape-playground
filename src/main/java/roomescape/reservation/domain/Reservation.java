package roomescape.reservation.domain;

import lombok.Getter;
import roomescape.time.domain.Time;

import java.time.LocalDate;

@Getter
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation(){
    }

    public Reservation(Long id, String name, LocalDate date, Time time){
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
