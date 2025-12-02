package roomescape.advice;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON-001", "잘못된 입력값입니다."),
    CANNOT_DELETE_RESERVATION(HttpStatus.BAD_REQUEST, "RES-002", "이미 예약이 존재하는 시간은 삭제할 수 없습니다."),

    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "RES-003", "예약을 찾을 수 없습니다."),
    TIME_NOT_FOUND(HttpStatus.NOT_FOUND, "TIME-001", "해당 시간을 찾을 수 없습니다."),

    IDEMPOTENCY_KEY_MISMATCH(HttpStatus.BAD_REQUEST, "BIZ-002", "멱등성 키 불일치: 요청 내용이 다릅니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}