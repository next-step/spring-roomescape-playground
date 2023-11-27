package roomescape.dto;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

import roomescape.domain.Reservation;

public class ReservationResponse {

    private final AtomicLong id;
    private final String name;
    private final LocalDate date;
    private final String time;

    private ReservationResponse(AtomicLong id, String name, LocalDate date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public AtomicLong getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
