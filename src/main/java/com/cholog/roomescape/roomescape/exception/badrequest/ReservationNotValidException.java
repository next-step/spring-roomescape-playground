package com.cholog.roomescape.roomescape.exception;

import com.cholog.roomescape.exception.BadRequestException;

public class ReservationNotValidException extends BadRequestException {
    public ReservationNotValidException(String message) {
        super(message);
    }
}
