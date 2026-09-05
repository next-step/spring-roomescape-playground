package com.cholog.roomescape.global.exception;

import com.cholog.roomescape.domain.exception.badrequest.ReservationNotValidException;

public class NotFoundException extends ReservationNotValidException {
    public NotFoundException(String message) {
        super(message);
    }
}
