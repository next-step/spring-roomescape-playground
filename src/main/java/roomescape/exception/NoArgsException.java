package roomescape.exception;

public class NoArgsException extends RuntimeException {

    public NoArgsException() {
        super("필요한 인자가 존재하지 않습니다.");
    }
}
