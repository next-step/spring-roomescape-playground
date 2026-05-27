package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.time.dto.TimeResponse;

public record ReservationResponse(Long id, String name, String date, TimeResponse time) {
    public static ReservationResponse fromReservation(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate().toString(),
                TimeResponse.fromTime(reservation.getTime()));
    }
}
