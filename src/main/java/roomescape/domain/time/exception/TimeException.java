package roomescape.domain.time.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import roomescape.global.error.BusinessException;

@Getter
public class TimeException extends BusinessException {
    private final HttpStatus httpStatus;
    private final String message;

    @Getter
    @RequiredArgsConstructor
    public enum TimeErrorCode {
        NOT_FOUND(BAD_REQUEST, "해당 예약을 찾을 수 없습니다."),
        ;

        private final HttpStatus status;
        private final String message;
    }

    public TimeException(TimeErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}
