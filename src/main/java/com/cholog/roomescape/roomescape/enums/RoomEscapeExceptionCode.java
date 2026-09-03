package com.cholog.roomescape.roomescape.exception.code;

public enum RoomEscapeExceptionCode {

    RESERVATION_NOT_FOUND("해당 예약을 찾을 수 없습니다."),
    RESERVATION_CONFLICT("이미 예약된 시각입니다."),

    TIME_NOT_FOUND("해당 시간을 찾을 수 없습니다."),
    TIME_CONFLICT("이미 존재하는 시각입니다."),
    ;

    private final String message;

    RoomEscapeExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
