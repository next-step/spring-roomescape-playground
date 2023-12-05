package roomescape.exception;

public class IdNotExistException extends RuntimeException {
    private static final String MESSAGE = "존재하지 않는 ID입니다.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
