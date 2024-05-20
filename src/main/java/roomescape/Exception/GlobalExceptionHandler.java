package roomescape.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> runTimeExceptionHandler(RuntimeException error) {

        System.out.println(error.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> illegalArgumentsExceptionHandler(IllegalArgumentException error) {

        System.out.println(error.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException error) {

        System.out.println(error.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
