package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.exception.InvalidParameterException;
import roomescape.global.exception.code.ErrorStatus;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public ReservationRequest {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new InvalidParameterException(ErrorStatus.INVALID_REQUEST_RESERVATION_INFO);
        }
    }

    public static Reservation toReservation(final ReservationRequest reservationRequest) {
        return new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
    }
}
