package roomescape.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequestDto(
        String name,
        LocalDate date,
        LocalTime time
) {

}
