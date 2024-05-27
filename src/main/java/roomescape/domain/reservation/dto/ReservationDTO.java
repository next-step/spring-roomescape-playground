package roomescape.domain.reservation.dto;

import roomescape.domain.time.Time;

public record ReservationDTO(long id,
                             String name,
                             String date,
                             Time time) {
}
