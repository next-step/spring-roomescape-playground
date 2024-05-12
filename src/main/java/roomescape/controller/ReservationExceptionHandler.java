package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import roomescape.exception.BadReservationSaveRequestException;
import roomescape.exception.NotFoundReservationException;

@ControllerAdvice
public class ReservationExceptionHandler {

	@ExceptionHandler(BadReservationSaveRequestException.class)
	public ResponseEntity<String> handleException(BadReservationSaveRequestException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(NotFoundReservationException.class)
	public ResponseEntity<String> handleException(NotFoundReservationException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
