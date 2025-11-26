package roomescape.dto;

import org.springframework.http.ResponseEntity;
import roomescape.advice.ErrorCode;

public class ErrorResponse {
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.getCode(),errorCode.getMessage()));
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
