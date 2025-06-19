package roomescape.exception;

public class InvalidRequestException extends IllegalArgumentException {
    public InvalidRequestException(String message) {
        super("비어있는 입력이 있습니다: " + message);
    }
}
