package roomescape.controller;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime dateTime) {
}
