package roomescape.domain.reservation.dto.request;

import roomescape.domain.reservation.entity.Reservation;

public record ReservationCreateRequestDto(String name, String date, String time) {
    public Reservation toEntity(Long id, String name, String date, String time) {
        return new Reservation(id, name, date, time);
    }
}