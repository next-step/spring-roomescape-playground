package roomescape.reservation.exception;

import roomescape.global.exception.code.ErrorStatus;

public class ReservationNotFoundException extends RuntimeException {
    ErrorStatus errorStatus;
    private final Long reservationId;

    public ReservationNotFoundException(ErrorStatus errorStatus, Long reservationId) {
        this.errorStatus = errorStatus;
        this.reservationId = reservationId;
    }

    public ErrorStatus getErrorCode() {
        return errorStatus;
    }

    public Long getReservationId() {
        return reservationId;
    }
}
