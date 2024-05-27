package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {

    EMPTY_DATE(HttpStatus.BAD_REQUEST, "날짜가 누락되었습니다."),
    EMPTY_NAME(HttpStatus.BAD_REQUEST, "이름이 누락되었습니다."),
    EMPTY_TIME(HttpStatus.BAD_REQUEST, "시간이 누락되었습니다."),
    INVALID_ID_REQUEST(HttpStatus.BAD_REQUEST, "유요하지 않은 예약삭제 요청입니다."),
    INVALID_TIME_ID(HttpStatus.BAD_REQUEST, "유요하지 않은 시간 ID입니다."),
    ;

    private HttpStatus status;
    private String message;

    ErrorMessage(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
