package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.global.exception.code.ErrorStatus;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.exception.InvalidParameterException;

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
