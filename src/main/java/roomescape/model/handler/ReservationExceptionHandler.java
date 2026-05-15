package roomescape.model.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.model.errors.ReservationNotFoundException;

@RestControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler(ReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleReservationNotFoundError(ReservationNotFoundException exception) {
        return "없는 예약번호 입니다.";
    }
}
