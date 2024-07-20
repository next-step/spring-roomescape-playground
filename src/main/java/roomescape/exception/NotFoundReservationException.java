package roomescape.exception;

public class NotFoundReservationException extends RuntimeException{

    private Long reservationId;
  
    public NotFoundReservationException(Long reservationId) {
        super("예약을 찾을 수 없습니다: " + reservationId);
        this.reservationId = reservationId;
    }

    public Long getReservationId() {
        return reservationId;
    }
}
