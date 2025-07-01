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
    public ReservationResponse(Reservation reservation) {
        this(
            reservation.getId(),
            reservation.getName(),
            DateTimeUtil.format(reservation.getDate()),
            new TimeResponse(reservation.getTime())
        );
    }

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(reservation);
    }
}
