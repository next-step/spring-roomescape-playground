package com.cholog.roomescape.roomescape.exception;

import com.cholog.roomescape.exception.NotFoundException;
import com.cholog.roomescape.roomescape.exception.code.RoomEscapeExceptionCode;

public class TimeNotFoundException extends NotFoundException {
    public TimeNotFoundException() {
        super(RoomEscapeExceptionCode.TIME_NOT_FOUND.getMessage());
    }
}
