package roomescape.controller.dto.request;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationCreateRequestDto(String name, String date, String timeId) {
    public Reservation toEntity(Long id, Time time) {
        return new Reservation(id, name, date, time);
    }
}
