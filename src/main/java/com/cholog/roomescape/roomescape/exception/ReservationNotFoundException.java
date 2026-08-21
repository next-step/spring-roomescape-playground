package com.cholog.roomescape.roomescape.exception;

import com.cholog.roomescape.roomescape.exception.code.RoomEscapeExceptionCode;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super(RoomEscapeExceptionCode.RESERVATION_NOT_FOUND.getMessage());
    }
}
