package roomescape.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private final Map<String, Object> arguments;
    private final HttpStatus httpStatus;

    public BusinessException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.arguments = new HashMap<>();
        this.httpStatus = httpStatus;
    }

    protected void addArgument(String key, Object value) {
        arguments.put(key, value);
    }
}
