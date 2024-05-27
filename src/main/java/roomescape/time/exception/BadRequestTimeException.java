package roomescape.time.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestTimeException extends RuntimeException{
    public BadRequestTimeException(String message){
        super(message);
    }
}
