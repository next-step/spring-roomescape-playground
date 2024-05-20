package roomescape.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long id) {
        super("예약 정보를 찾을 수 없습니다. ID: " + id);
    }
}
