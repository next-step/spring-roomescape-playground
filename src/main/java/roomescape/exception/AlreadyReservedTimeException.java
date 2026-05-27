package roomescape.exception;

public class AlreadyReservedTimeException extends RuntimeException {
    public AlreadyReservedTimeException() {
        super("이미 예약된 시간입니다.");
    }
}

