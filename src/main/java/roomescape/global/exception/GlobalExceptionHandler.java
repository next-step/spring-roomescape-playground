package roomescape.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.reservations.exception.*;
import roomescape.timeslot.exception.TimeslotException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleReservationRequestWithMissingArgumentsException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<String> handleReservationException(ReservationException e) {
        log.error("[error] Reservation 진행중 오류. 오류 데이터 원본: {}, 에러 메시지: {}", e.getDebugData(), e.getMessage());
        return ResponseEntity.badRequest().body("무언가 잘못됐어요! 다시 시도해주세요.");
    }

    @ExceptionHandler(TimeslotException.class)
    public ResponseEntity<String> handleTimeslotException(TimeslotException e) {
        log.error("[error]: Timeslot 진행중 오류. 오류 데이터 원본: {}, 에러 메시지: {}", e.getDebugData(), e.getMessage());
        return ResponseEntity.badRequest().body("무언가 잘못됐어요! 다시 시도해주세요.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> catchMysteryException(Exception e) {
        log.error("[error] 원인 불명 에러발. 에러메시지: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body("무언가 잘못됐어요! 다시 시도해주세요.");
    }
}
