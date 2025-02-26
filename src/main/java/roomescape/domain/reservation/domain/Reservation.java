package roomescape.domain.reservation.domain;

import java.time.LocalDate;
import roomescape.domain.reservationTime.domain.ReservationTime;

public class Reservation {

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final ReservationTime reservationTime;

    public static Reservation newWithoutId(final String name, final LocalDate date, final ReservationTime reservationTime) {
        return new Reservation(null, name, date, reservationTime);
    }

    public Reservation(final Long id, final String name, final LocalDate date, final ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
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

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

}
