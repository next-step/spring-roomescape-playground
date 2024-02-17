package roomescape.exception.handler;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import roomescape.exception.ReservationException;

@RestControllerAdvice
@NonNullApi
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
															 HttpStatusCode statusCode, WebRequest request) {
		if (ex instanceof ReservationException) {
			return handleReservationException((ReservationException) ex, headers, request);
		}
		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}

	private ResponseEntity<Object> handleReservationException(ReservationException ex, HttpHeaders headers,
															  WebRequest request) {
		return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.BAD_REQUEST);
	}
}
