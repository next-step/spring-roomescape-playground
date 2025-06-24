package roomescape.reservation.domain;

import roomescape.reservation.dto.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {
    //memory 용
    public static Reservation of(ReservationRequest request, Long id) {
        return new Reservation (id, request.name(), request.date(), request.time());
    }

    //DB 용
    public static Reservation of(ReservationRequest request) {
        return new Reservation(null, request.name(), request.date(), request.time());
    }
}
