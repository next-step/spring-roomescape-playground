package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.RoomEscapeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(
            IllegalArgumentException e
    ) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(RoomEscapeException.class)
    public ResponseEntity<Void> handleRoomEscapeException(RoomEscapeException e) {
        return ResponseEntity.status(e.getStatus()).build();
    }
}
