package roomescape.global.exception;

import org.springframework.http.HttpStatus;

public class RoomescapeServerException extends RoomescapeException {

    public RoomescapeServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다...");
    }
}
