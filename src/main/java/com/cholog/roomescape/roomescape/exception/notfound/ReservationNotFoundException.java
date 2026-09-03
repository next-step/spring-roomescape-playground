package com.cholog.roomescape.roomescape.exception;

import com.cholog.roomescape.exception.NotFoundException;
import com.cholog.roomescape.roomescape.exception.code.RoomEscapeExceptionCode;

public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException() {
        super(RoomEscapeExceptionCode.RESERVATION_NOT_FOUND.getMessage());
    }
}
