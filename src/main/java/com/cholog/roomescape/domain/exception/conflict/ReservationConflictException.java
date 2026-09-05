package com.cholog.roomescape.domain.exception.conflict;

import com.cholog.roomescape.global.exception.ConflictException;

public class ReservationConflictException extends ConflictException {
    public ReservationConflictException(String message) {
        super(message);
    }
}
