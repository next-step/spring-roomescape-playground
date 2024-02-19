package roomescape.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_ARGUMENT("입력 정보가 부족합니다."),
    NOT_EXIST_RESERVATION("삭제할 예약이 없습니다."),
    NOT_EXIST_TIME("삭제할 시간이 없습니다.");

    private final String message;
}