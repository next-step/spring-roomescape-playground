package roomescape.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_FOUND_RESERVATION(400, "삭제할 예약이 없습니다."),
    NO_PARAMETER(400, "필요한 인자가 없습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }
}
