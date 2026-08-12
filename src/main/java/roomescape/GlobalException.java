package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.InvalidReservationRequestException;
import roomescape.exception.NotFoundReservationException;

@ControllerAdvice
public class GlobalException {

    // 없는 예약 삭제 오류
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    //예약 입력 값 누락 오류
    @ExceptionHandler(InvalidReservationRequestException.class)
    public ResponseEntity<Void> handleInvalidRequestException() {
        return ResponseEntity.badRequest().build();
    }

    //날짜, 시간 형식 오류
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> handleHttpMessageNotReadableException() {
        return ResponseEntity.badRequest().build();
    }
}
