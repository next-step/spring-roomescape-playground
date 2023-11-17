package roomescape.exception;

import roomescape.utils.ErrorMessage;

public class NotFoundReservationException extends RuntimeException {
    public NotFoundReservationException(ErrorMessage message) {
        super(message.getMessage());
    }
}
