package roomescape.dto;

import roomescape.model.Reservation;
public record ReservationResponse(
            String name,
            String date,
            String time
    )
    {
        public static ReservationResponse from(Reservation reservation) {
            return new ReservationResponse(
                    reservation.name(),
                    reservation.date(),
                    reservation.time()
            );
        }
    }

