package roomescape.dto;

import roomescape.domain.Reservation;

public record ReservationRequestDto(String name, String date, String time) {

    public static ReservationRequestDto convertToDto(Reservation reservation) {
        return new ReservationRequestDto(reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, time);
    }
}
