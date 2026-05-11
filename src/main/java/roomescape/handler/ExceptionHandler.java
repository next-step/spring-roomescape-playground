package roomescape.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundReservationException;

@ControllerAdvice //전체 컨트롤러의 예외를 관리하는 클래스
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({NotFoundReservationException.class, BadRequestException.class})
    public ResponseEntity<Void> handleBadRequestException(RuntimeException e){//발생한 예외 객체
        return ResponseEntity.badRequest().build(); //HTTP 400 Bad Request 반환
    }
}
