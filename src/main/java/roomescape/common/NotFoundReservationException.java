package roomescape.common;

import java.util.NoSuchElementException;

public class NotFoundReservationException extends NoSuchElementException {

	public NotFoundReservationException(String message) {
		super(message);
	}
}
