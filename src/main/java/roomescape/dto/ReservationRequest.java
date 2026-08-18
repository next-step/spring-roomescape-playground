package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationException;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        String name,
        String date,
        String time
) {
    public Reservation toReservation() {
        validate();
        return new Reservation(
                name,
                LocalDate.parse(date),
                LocalTime.parse(time)
        );
    }

    private void validate() {
        if (isBlank(name) || isBlank(date) || isBlank(time)) {
            throw new InvalidReservationException("예약에 필요한 인자가 없습니다.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
