package roomescape.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    // 400 : BAD_REQUEST
    EMPTY_VALUE_REQUEST("비어있는 값이 존재합니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_RESERVATION("해당하는 예약을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    TRANSACTION_ERROR("데이터 베이스 반영에 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
    // 404 : NOT_FOUND

    private final String message;
    private final HttpStatus httpStatus;
}
