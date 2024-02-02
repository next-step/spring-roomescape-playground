package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationRequestDto(String name, String date, Long timeId) {

    public static ReservationRequestDto convertToDto(Reservation reservation) {
        return new ReservationRequestDto(reservation.getName(), reservation.getDate(), reservation.getTime().getId());
    }

    public Reservation toEntity(Time time) {
        return new Reservation(null, name, date, time);
    }
}
