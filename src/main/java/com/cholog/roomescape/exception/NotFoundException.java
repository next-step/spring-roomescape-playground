package com.cholog.roomescape.exception;

import com.cholog.roomescape.roomescape.exception.ReservationNotValidException;

public class NotFoundException extends ReservationNotValidException {
    public NotFoundException(String message) {
        super(message);
    }
}
