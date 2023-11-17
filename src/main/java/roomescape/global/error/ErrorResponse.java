package roomescape.global.error;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class ErrorResponse {
    private final String message;

    public static ErrorResponse from(String message) {
        return new ErrorResponse(message);
    }
}
