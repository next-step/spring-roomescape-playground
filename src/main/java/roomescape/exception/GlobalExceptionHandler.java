package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.dto.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundReservationException(NotFoundReservationException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(BlankReservationException.class)
    public ResponseEntity<ExceptionResponse> handleBlankReservationException(BlankReservationException ex){
        return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(ReservationSaveFailedException.class)
    public ResponseEntity<ExceptionResponse> handleReservationSaveFailedException(ReservationSaveFailedException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundReservationTimeException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundReservationTimeException(NotFoundReservationTimeException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateReservationTimeException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateReservationTimeException(DuplicateReservationTimeException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(ex.getMessage()));
    }
}
