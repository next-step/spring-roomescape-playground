package roomescape.reservation.dto.response;

import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponse {
    public record createReservationDto(
            Long id,
            String name,
            LocalDate date,
            LocalTime time
    ) {
        public createReservationDto(Reservation reservation) {
            this(
                    reservation.getId(),
                    reservation.getName(),
                    reservation.getDate(),
                    reservation.getTime()
            );
        }
    }
}
