package com.cholog.roomescape.domain.exception.notfound;

import com.cholog.roomescape.global.exception.NotFoundException;
import com.cholog.roomescape.domain.enums.RoomEscapeExceptionCode;

public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException() {
        super(RoomEscapeExceptionCode.RESERVATION_NOT_FOUND.getMessage());
    }
}
