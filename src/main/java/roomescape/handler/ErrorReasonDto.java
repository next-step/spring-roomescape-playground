package roomescape.handler;

import org.springframework.http.HttpStatus;

public record ErrorReasonDto  (
    HttpStatus status,
    String code
) {
}
