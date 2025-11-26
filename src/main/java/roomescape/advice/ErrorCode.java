package roomescape.advice;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON-001", "잘못된 입력값입니다."),

    DUPLICATE_DATA(HttpStatus.CONFLICT, "BIZ-001", "이미 처리된 요청이거나 중복된 데이터입니다."),
    IDEMPOTENCY_KEY_MISMATCH(HttpStatus.BAD_REQUEST, "BIZ-002", "멱등성 키 불일치: 요청 내용이 다릅니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
