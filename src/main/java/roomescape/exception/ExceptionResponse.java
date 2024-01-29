package roomescape.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime dateTime) {
}
