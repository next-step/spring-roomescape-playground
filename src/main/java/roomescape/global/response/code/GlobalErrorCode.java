package roomescape.global.response.code;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ErrorCode {

    BAD_REQUEST_ERROR("GLOBAL_BAD_REQUEST", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_HTTP_MESSAGE_BODY("GLOBAL_INVALID_BODY", HttpStatus.BAD_REQUEST, "요청 본문 형식이 올바르지 않습니다."),
    INVALID_HTTP_MESSAGE_PARAMETER("GLOBAL_INVALID_PARAMETER", HttpStatus.BAD_REQUEST, "요청 값의 형식이 올바르지 않습니다."),
    METHOD_NOT_ALLOWED("GLOBAL_METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다."),
    UNSUPPORTED_MEDIA_TYPE("GLOBAL_UNSUPPORTED_MEDIA_TYPE", HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 Content-Type입니다."),
    SERVER_ERROR("GLOBAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 알 수 없는 오류가 발생했습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    GlobalErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
