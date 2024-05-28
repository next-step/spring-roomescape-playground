package roomescape.reservation.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import roomescape.reservation.presentation.exception.BadReservationSaveRequestException;
import roomescape.reservation.presentation.exception.NotFoundReservationException;

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
