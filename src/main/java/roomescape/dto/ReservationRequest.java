package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationRequestException;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest (
    String name,
    LocalDate date,
    LocalTime time
) {
    public Reservation toEntity(long id) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new InvalidReservationRequestException("예약 정보는 모두 입력해야 합니다.");
        }
        return new Reservation(id, name, date,time);
    }
}
