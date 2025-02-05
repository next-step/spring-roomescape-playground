package roomescape.domain.reservation.error;

import roomescape.domain.common.error.BusinessException;

public class ReservationException extends BusinessException {

    public ReservationException() {
        super();
    }

    public ReservationException(String message) {
        super(message);
    }
}
