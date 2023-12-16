package roomescape.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.exception.ValidationException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ReservationExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ReservationExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public ResponseEntity<Void> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(roomescape.exception.ValidationException.class)
    @ResponseBody
    public ResponseEntity<Void> handleValidationException(ValidationException e) {
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(error -> {
            logger.error(error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().build();
    }
}
