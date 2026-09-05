package com.cholog.roomescape.domain.exception.notfound;

import com.cholog.roomescape.global.exception.NotFoundException;
import com.cholog.roomescape.domain.enums.RoomEscapeExceptionCode;

public class TimeNotFoundException extends NotFoundException {
    public TimeNotFoundException() {
        super(RoomEscapeExceptionCode.TIME_NOT_FOUND.getMessage());
    }
}
