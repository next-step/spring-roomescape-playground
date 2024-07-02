package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponse extends Reservation {
    public ReservationResponse() {
        super();
    }

    public ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {
        super(id, name, date, time);
    }

    public static ReservationResponse toEntity(Long id, ReservationRequest request) {
        return new ReservationResponse(id, request.getName(), request.getDate(), request.getTime());
    }
}
