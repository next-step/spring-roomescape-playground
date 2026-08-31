package com.cholog.roomescape.roomescape.exception.code;

public enum RoomEscapeExceptionCode {

    RESERVATION_NOT_FOUND("해당 예약을 찾을 수 없습니다."),

    TIME_NOT_FOUND("해당 시간을 찾을 수 없습니다.")
    ;

    private final String message;

    RoomEscapeExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
