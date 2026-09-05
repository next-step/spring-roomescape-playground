package com.cholog.roomescape.domain.exception.badrequest;

import com.cholog.roomescape.global.exception.BadRequestException;

public class ReservationNotValidException extends BadRequestException {
    public ReservationNotValidException(String message) {
        super(message);
    }
}
