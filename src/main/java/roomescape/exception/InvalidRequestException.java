package roomescape.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends BusinessException {
    public InvalidRequestException(String fieldName) {
        super("request.invalid", "비어있는 입력이 있습니다: " + fieldName, HttpStatus.BAD_REQUEST);
        addArgument("field", fieldName);
    }
}
