package roomescape.exception;

public class TimeNotFoundException extends RuntimeException {
    public TimeNotFoundException(Long id) {
        super("존재하지 않는 시간입니다. id : " + id);
    }
}
