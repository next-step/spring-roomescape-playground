package roomescape.global.exception.code;

import org.springframework.http.HttpStatus;

public record ErrorDto(HttpStatus status, String message) {
}
