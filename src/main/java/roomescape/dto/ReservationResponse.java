package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        String time
) {
    public static ReservationResponse from(Reservation reservation) {
        String formattedTime = reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                formattedTime
        );
    }
}
