package roomescape.domain.reservation.exception;

import roomescape.global.exception.RoomescapeNotFoundException;
import roomescape.global.exception.code.ErrorStatus;

public class ReservationNotFoundException extends RoomescapeNotFoundException {

    public ReservationNotFoundException(final ErrorStatus errorStatus, final Long data) {
        super(errorStatus, data);
    }
}
