package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {
    public static ReservationResponse from(Reservation reservation) {
        LocalDateTime reservedAt = reservation.getReservedAt();

        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservedAt.toLocalDate().toString(),
                reservedAt.toLocalTime().toString()
        );
    }
}
