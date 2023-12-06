package roomescape.global;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    RESERVATION_NOT_FOUND("해당하는 예약이 없습니다.", HttpStatus.BAD_REQUEST),
    TIME_NOT_FOUND("해당하는 시간이 없습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
