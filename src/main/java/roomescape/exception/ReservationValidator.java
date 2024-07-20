package roomescape.exception;

import roomescape.dto.ReservationRequestDto;

public class ReservationValidator {
    public static void validate(ReservationRequestDto reservation) {
        if (reservation.getName() == null || reservation.getName().isEmpty()) {
            throw new InvalidReservationException("name", "이름이 필요합니다.");
        }
        if (reservation.getDate() == null || reservation.getDate().isEmpty()) {
            throw new InvalidReservationException("date", "날짜가 필요합니다.");
        }
        if (reservation.getTime() == null || reservation.getTime().isEmpty()) {
            throw new InvalidReservationException("time", "시간이 필요합니다.");
        }
        if (!reservation.getTime().matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            throw new InvalidReservationException("time", "시간 형식이 잘못되었습니다.");
        }
    }
}
