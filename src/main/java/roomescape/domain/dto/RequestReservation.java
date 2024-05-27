package roomescape.domain.dto;

import roomescape.domain.Time;
import roomescape.domain.exception.BadRequestReservation;

public record RequestReservation(
        String name,
        String date,
        Time time
) {
    public RequestReservation{
        if (name.isBlank() || date.isBlank() || time == null) {
            throw new BadRequestReservation("예약에 필요한 인자가 부족합니다.");
        }
    }
}
