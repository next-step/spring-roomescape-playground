package roomescape.exception;

import org.springframework.http.HttpStatus;

public class TimeNotFoundException extends BusinessException {
    public TimeNotFoundException(Long id) {
        super("time.notFound", "존재하지 않는 시간입니다. id : " + id, HttpStatus.NOT_FOUND);
        addArgument("id", id);
    }
}
