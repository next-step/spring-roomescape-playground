package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.format.DateTimeFormatter;

public record ReservationResponse(
    Long id,
    String name,
    String date,
    TimeResponse time
) {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().format(DATE_FORMATTER),
                TimeResponse.from(reservation.getTime())
        );
    }
}

