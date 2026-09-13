package roomescape;

import java.time.LocalDate;
import roomescape.exception.InvalidReservationException;

public record ReservationRequest(String name, LocalDate date, Long time) {

    public void validate() {
        if (isBlank(name) || date == null || time == null) {
            throw new InvalidReservationException("예약 정보는 비어 있을 수 없습니다.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getTime() {
        return time;
    }
}
