package com.cholog.roomescape.roomescape.exception.notfound;

import com.cholog.roomescape.exception.NotFoundException;
import com.cholog.roomescape.roomescape.enums.RoomEscapeExceptionCode;

public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException() {
        super(RoomEscapeExceptionCode.RESERVATION_NOT_FOUND.getMessage());
    }
}
