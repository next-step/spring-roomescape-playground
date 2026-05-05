package roomescape.exceptions;

public class EmptyDateTimeException extends RuntimeException {
    public EmptyDateTimeException() {
        super("예약 시간이 비어 있을 수 없습니다.");
    }
}
