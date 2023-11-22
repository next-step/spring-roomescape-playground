package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(DomainEmptyFieldException.class)
	public ResponseEntity handleException(DomainEmptyFieldException e) {
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(NotFoundReservationException.class)
	public ResponseEntity handleException(NotFoundReservationException e) {
		return ResponseEntity.badRequest().build();
	}
}
