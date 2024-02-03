package roomescape.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationException extends RuntimeException {

    private ReservationErrorCode reservationErrorCode;
    private String message;

    public ReservationException(ReservationErrorCode reservationErrorCode) {
        this.reservationErrorCode = reservationErrorCode;
        this.message = reservationErrorCode.getMessage();
    }
}
