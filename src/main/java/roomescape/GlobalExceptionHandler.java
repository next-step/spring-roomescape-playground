package roomescape;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 없는 예약 삭제 오류
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleNotFoundReservationException(NotFoundReservationException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // 없는 시간 삭제 오류
    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity<String> handleNotFoundTimeException(NotFoundTimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    //예약 입력 값 누락 오류
    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<String> handleInvalidRequestException(InvalidReservationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    //날짜, 시간 형식 오류
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("날짜 또는 시간 형식이 올바르지 않습니다.");
    }

    //예약 중복 오류
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> handleDuplicateReservationException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 해당 날짜와 시간에 예약이 존재합니다.");
    }

    //시간 값 누락 오류
    @ExceptionHandler(InvalidTimeException.class)
    public ResponseEntity<String> handleInvalidTimeException(InvalidTimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    //예약에서 사용 중인 시간 삭제 오류
    @ExceptionHandler(TimeInUseException.class)
    public ResponseEntity<String> handleTimeInUseException(TimeInUseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    // 시간 중복 오류
    @ExceptionHandler(DuplicateTimeException.class)
    public ResponseEntity<String> handleDuplicateTimeException(DuplicateTimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
