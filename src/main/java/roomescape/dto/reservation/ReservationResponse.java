package roomescape.dto.reservation;

import roomescape.domain.ReservationDomain;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {

    public static ReservationResponse from(ReservationDomain reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getTime()
        );
    }
}
