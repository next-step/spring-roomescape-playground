package roomescape.reservation.dto.response;

import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

import java.time.LocalDate;

public class ReservationResponse {
    public record createReservationDto(
            Long id,
            String name,
            LocalDate date,
            Time time
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
