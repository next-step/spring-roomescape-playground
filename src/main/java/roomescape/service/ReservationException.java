package roomescape.service;

public class ReservationException extends RuntimeException {

    public ReservationException(String message) {
        super(message);
    }

    public static class NotFoundReservationException extends ReservationException {
        public NotFoundReservationException() {
            super("해당 예약을 찾을 수 없습니다.");
        }

        public NotFoundReservationException(String message) {
            super(message);
        }
    }

    public static class DuplicateTimeException extends ReservationException {
        public DuplicateTimeException() {
            super("시간이 중복되었습니다");
        }

        public DuplicateTimeException(String message) {
            super(message);
        }
    }

    public static class InvalidReservationException extends ReservationException {
        public InvalidReservationException() {
            super("유효하지 않은 예약입니다.");
        }

        public InvalidReservationException(String message) {
            super(message);
        }
    }

}

