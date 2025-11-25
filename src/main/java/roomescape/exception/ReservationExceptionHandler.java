package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ReservationExceptionHandler {

    private static final Logger logger = Logger.getLogger("테스트용");

    @ExceptionHandler({
            InvalidReservationRequestException.class,
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,              
            MethodArgumentTypeMismatchException.class,          
            MissingServletRequestParameterException.class      
    })
    public ResponseEntity<Void> handleBadRequest(RuntimeException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFound(NotFoundReservationException e) {
        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
        logger.log(Level.INFO, stackTrace);
        return ResponseEntity.status(404).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleUnexpected(Exception e) {
        return ResponseEntity.status(500).build();
    }
}
