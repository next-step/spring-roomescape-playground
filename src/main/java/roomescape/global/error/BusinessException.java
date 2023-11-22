package roomescape.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {
    private final String message;
}
