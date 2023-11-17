package roomescape.global.error.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class ReservationException extends BusinessException {
    private final HttpStatus httpStatus;
    private final String message;

    @Getter
    @RequiredArgsConstructor
    public enum ErrorCode {
        NOT_FOUND(BAD_REQUEST, "해당 예약을 찾을 수 없습니다."),
        ;

        private final HttpStatus status;
        private final String message;
    }

    public ReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}
