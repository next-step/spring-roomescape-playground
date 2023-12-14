package roomescape.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(DomainEmptyFieldException.class)
	public ResponseEntity<Void> handleException(DomainEmptyFieldException e) {
		logger.warn("warn", e);
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(NotFoundReservationException.class)
	public ResponseEntity<Void> handleException(NotFoundReservationException e) {
		logger.warn("warn", e);
		return ResponseEntity.badRequest().build();
	}
}
