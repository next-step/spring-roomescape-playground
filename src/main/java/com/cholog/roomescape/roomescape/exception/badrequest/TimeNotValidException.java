package com.cholog.roomescape.roomescape.exception.badrequest;

import com.cholog.roomescape.exception.BadRequestException;

public class TimeNotValidException extends BadRequestException {
    public TimeNotValidException(String message) {
        super(message);
    }
}
