package roomescape.reservation.domain;

import java.time.LocalDate;
import roomescape.time.domain.Time;

public class Reservation {
    public static final int RESERVATION_LENGTH_MINUTES = 60;

    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation(String name, LocalDate date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private Reservation(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation withId(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }

    public Long getId() {
        return id;
    }

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
