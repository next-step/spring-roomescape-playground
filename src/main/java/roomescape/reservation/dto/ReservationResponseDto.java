package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record ReservationResponseDto(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
}
