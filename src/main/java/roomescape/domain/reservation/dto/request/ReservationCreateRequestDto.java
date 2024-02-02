package roomescape.domain.reservation.dto.request;

import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.time.entity.Time;

public record ReservationCreateRequestDto(String name, String date, String timeId) {
    public Reservation toEntity(Long id, Time time) {
        return new Reservation(id, name, date, time);
    }
}
