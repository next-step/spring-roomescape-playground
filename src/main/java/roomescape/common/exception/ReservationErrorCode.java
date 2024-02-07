package roomescape.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ReservationErrorCode implements ErrorCode {

    INVALID_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, "필요한 인자가 채워지지 않았습니다."),
    NO_DELETE_RESERVATION_FOUND(HttpStatus.BAD_REQUEST, "삭제할 예약이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
