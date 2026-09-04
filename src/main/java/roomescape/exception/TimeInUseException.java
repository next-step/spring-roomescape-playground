package roomescape.exception;

public final class TimeInUseException extends RuntimeException {

    public TimeInUseException(Long id) {
        super("예약에서 사용 중인 시간은 삭제할 수 없습니다. id=" + id);
    }
}
