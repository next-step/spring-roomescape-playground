package roomescape.common;

import java.util.NoSuchElementException;

public class NotFoundTimeException extends NoSuchElementException {

	public NotFoundTimeException(String message) {
		super(message);
	}
}
