package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestReservationException extends RuntimeException{
    public BadRequestReservationException(String message){
        super(message);
    }
}
