package roomescape.controller.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        String time
) {

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getTime()
        );
    }

    public static List<ReservationResponse> of(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }
}
