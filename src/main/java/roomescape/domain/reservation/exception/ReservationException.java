package roomescape.domain.reservation.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import roomescape.global.error.BusinessException;

@Getter
public class ReservationException extends BusinessException {
    private final HttpStatus httpStatus;
    private final String message;

    @Getter
    @RequiredArgsConstructor
    public enum ErrorCode {
        NOT_FOUND(BAD_REQUEST, "해당 예약을 찾을 수 없습니다."),
        DUPLICATED(BAD_REQUEST, "중복된 예약입니다."),
        INVALID_VALUE(BAD_REQUEST, "입력 값이 유효하지 않습니다."),
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
