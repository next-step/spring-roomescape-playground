package roomescape;

public class ReservationTimeInUseException extends RuntimeException {

    public ReservationTimeInUseException(Throwable cause) {
        super("예약에서 사용 중인 시간은 삭제할 수 없습니다.", cause);
    }
}