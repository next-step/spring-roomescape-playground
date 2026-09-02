package roomescape;

import roomescape.exception.InvalidReservationException;

public record ReservationRequest(String name, String date, String time) {

    public void validate() {
        if (isBlank(name) || isBlank(date) || isBlank(time)) {
            throw new InvalidReservationException("예약 정보는 비어 있을 수 없습니다.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
