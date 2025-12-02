package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity NotFoundReservationException() { //딱히 메시지에 id 등 추가할 필요를 못 느껴서 매개변수 없앰.
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(InvalidRequestReservationException.class)
    public ResponseEntity InvalidRequestReservationException(){
        return ResponseEntity.badRequest().build();
    }

}
