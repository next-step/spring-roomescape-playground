package roomescape.exception;

public class TimeNotExistException extends RuntimeException {

    private static final String MESSAGE = "등록되지 않은 시간입니다.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
