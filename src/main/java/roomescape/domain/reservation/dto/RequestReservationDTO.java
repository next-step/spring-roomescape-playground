package roomescape.domain.reservation.dto;

import roomescape.domain.reservation.Reservation;

public record RequestReservationDTO(String name,
                                    String date,
                                    String time) {

    public Reservation toReservaiton() {
        return new Reservation(
                name,
                date,
                time);
    }
}
