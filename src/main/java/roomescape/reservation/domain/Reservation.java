package roomescape.reservation.domain;

import java.time.LocalDateTime;

public record Reservation(
        Long id,
        String name,
        LocalDateTime reservationTime
) {
}
