package roomescape.exception;

import java.util.Map;

public record ErrorResponse(String code, String message, Map<String, Object> arguments) {
    public static ErrorResponse from(BusinessException e) {
        return new ErrorResponse(e.getCode(), e.getMessage(), e.getArguments());
    }
}
