package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum FailMessage {
    //400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 40000, "잘못된 요청입니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "DB 처리 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    FailMessage(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
