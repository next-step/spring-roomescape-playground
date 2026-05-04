package roomescape.exceptions;

public class EmptyNameException extends RuntimeException {
    public EmptyNameException() {
        super("이름이 비어있을 수 없습니다.");
    }
}
