package roomescape.domain.reservation.domain;

import java.sql.Time;
import java.time.LocalDate;
import roomescape.domain.reservationTime.domain.ReservationTime;

public class Reservation {

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final ReservationTime time;

    public static Reservation newWithoutId(final String name, final LocalDate date, final ReservationTime time) {
        return new Reservation(null, name, date, time);
    }

    public Reservation(final Long id, final String name, final LocalDate date, final ReservationTime time) {
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
