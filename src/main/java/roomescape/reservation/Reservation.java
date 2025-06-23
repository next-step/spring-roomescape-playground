package roomescape.reservation;

import roomescape.exception.BadRequestException;
import roomescape.reservation.dto.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {
    public static Reservation of(ReservationRequest request, Long id) {
        return new Reservation (id, request.name(), request.date(), request.time());
    }


}
