package com.cholog.roomescape.domain.exception.badrequest;

import com.cholog.roomescape.global.exception.BadRequestException;

public class TimeNotValidException extends BadRequestException {
    public TimeNotValidException(String message) {
        super(message);
    }
}
