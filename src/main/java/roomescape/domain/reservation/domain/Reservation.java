package roomescape.domain.reservation.domain;

import java.time.LocalDate;

import roomescape.domain.time.domain.Time;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation() {

    }

    public Reservation(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

}
