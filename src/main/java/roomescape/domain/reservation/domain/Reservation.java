package roomescape.domain.reservation.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final Time time;

    public static Reservation newWithoutId(final String name, final LocalDate date, final LocalTime time,
                                           final Time time2) {
        return new Reservation(null, name, date, time2);
    }

    public Reservation(final Long id, final String name, final LocalDate date, final Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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
