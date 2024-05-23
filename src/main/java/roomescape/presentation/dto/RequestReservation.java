package roomescape.presentation.dto;

import roomescape.domain.Reservation;
import roomescape.presentation.exception.BadRequestReservation;

public record RequestReservation(
        String name,
        String date,
        String time
) {

    private void validateParams() {
        if (date.isBlank() || name.isBlank() || time.isBlank()) {
            throw new BadRequestReservation("예약에 필요한 인자가 부족합니다.");
        }
    }

    public Reservation toReservation() {
        validateParams();
        return new Reservation(name, date, time);
    }
}
