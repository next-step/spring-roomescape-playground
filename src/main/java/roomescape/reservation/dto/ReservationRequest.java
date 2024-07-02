package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest extends Reservation {
    public ReservationRequest() {
        super();
    }

    public ReservationRequest(String name, LocalDate date, LocalTime time) {
        super(null, name, date, time);
    }
}
