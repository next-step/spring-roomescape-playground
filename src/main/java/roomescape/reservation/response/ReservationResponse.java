package roomescape.reservation.response;

import roomescape.reservation.model.Reservation;
import roomescape.time.response.TimeResponse;
import roomescape.util.DateTimeUtil;

public record ReservationResponse(
    Long id,
    String name,
    String date,
    TimeResponse time
) {

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(
            reservation.id(),
            reservation.name(),
            DateTimeUtil.format(reservation.date()),
            new TimeResponse(reservation.time())
        );
    }
}
