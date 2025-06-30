package roomescape.exception;

import java.time.LocalTime;

public class DuplicateTimeException extends RuntimeException {
    public DuplicateTimeException(LocalTime time) {
        super("이미 존재하는 시간입니다: " + time);
    }
}
