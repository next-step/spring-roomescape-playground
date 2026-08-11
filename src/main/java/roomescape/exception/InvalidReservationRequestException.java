package roomescape.exception;

public class InvalidReservationRequestException extends RuntimeException {

    public InvalidReservationRequestException() {
        super("예약 정보를 모두 입력해야 합니다.");
    }
}
