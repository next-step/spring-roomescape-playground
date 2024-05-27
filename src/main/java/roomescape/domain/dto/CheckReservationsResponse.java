package roomescape.domain.dto;

import roomescape.domain.Time;

public record CheckReservationsResponse(
        Long id,
        String name,
        String date,
        Time time
) {

}
