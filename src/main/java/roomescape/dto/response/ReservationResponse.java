package roomescape.dto.response;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        long id,

        String customerName,

        LocalDate date,

        LocalTime time
) {
    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(),
                reservation.getCustomerName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
