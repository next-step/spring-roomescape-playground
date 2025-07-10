package roomescape.exception;

import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends BusinessException {
    public ReservationNotFoundException(Long id) {
        super("reservation.notFound", "존재하지 않는 예약입니다. id : " + id, HttpStatus.NOT_FOUND);
        addArgument("id", id);
    }
}
