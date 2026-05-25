package roomescape.dto;

import roomescape.exception.InvalidReservationException;

public record ReservationRequest(
        Long id,
        String name,
        String date,
        String time
) {
    public ReservationRequest {
        validateReservationRequest(name, date, time);
    }

    private void validateReservationRequest(String name, String date, String time) {
        if (name == null || name.isBlank()) {
            throw new InvalidReservationException("ERROR: 이름을 작성하여야 합니다.");
        }
        if (date == null || date.isBlank()) {
            throw new InvalidReservationException("ERROR: 날짜를 작성하여야 합니다.");
        }
        if (time == null || time.isBlank()) {
            throw new InvalidReservationException("ERROR: 시간을 작성하여야 합니다.");
        }
    }
}
