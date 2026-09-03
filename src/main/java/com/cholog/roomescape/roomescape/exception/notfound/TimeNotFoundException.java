package com.cholog.roomescape.roomescape.exception.notfound;

import com.cholog.roomescape.exception.NotFoundException;
import com.cholog.roomescape.roomescape.enums.RoomEscapeExceptionCode;

public class TimeNotFoundException extends NotFoundException {
    public TimeNotFoundException() {
        super(RoomEscapeExceptionCode.TIME_NOT_FOUND.getMessage());
    }
}
