package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalTime;

public record ReservationResponseDTO(
        Long id,
        String name,
        String date,
        Long timeId,
        LocalTime time
) {
    public ReservationResponseDTO(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getId(),
                reservation.getTime().getTime()
        );
    }
}