package roomescape.exception;

public final class DuplicateReservationTimeException extends RuntimeException {

    public DuplicateReservationTimeException() {
        super("이미 등록된 시간입니다.");
    }
}
