package roomescape.time.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import roomescape.time.presentation.exception.BadTimeSaveRequestException;
import roomescape.time.presentation.exception.NotFoundTimeException;

@ControllerAdvice
public class TimeExceptionHandler {

	@ExceptionHandler(BadTimeSaveRequestException.class)
	public ResponseEntity<String> handleException(BadTimeSaveRequestException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(NotFoundTimeException.class)
	public ResponseEntity<String> handleException(NotFoundTimeException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
