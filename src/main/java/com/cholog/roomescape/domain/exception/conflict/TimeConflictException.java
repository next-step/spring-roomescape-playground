package com.cholog.roomescape.domain.exception.conflict;

import com.cholog.roomescape.global.exception.ConflictException;

public class TimeConflictException extends ConflictException {
    public TimeConflictException(String message) {
        super(message);
    }
}
