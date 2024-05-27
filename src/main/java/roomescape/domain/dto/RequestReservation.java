package roomescape.domain.dto;

import roomescape.domain.exception.BadRequestReservation;

public record RequestReservation(
        String name,
        String date,
        String time
) {
    public RequestReservation{
        if (name.isBlank() || date.isBlank() || time.isBlank()) {
            throw new BadRequestReservation("예약에 필요한 인자가 부족합니다.");
        }
    }

//    private void validateParams() {
//        if (this.name.isBlank() || date.isBlank() || time.isBlank()) {
//            throw new BadRequestReservation("예약에 필요한 인자가 부족합니다.");
//        }
//    }
}
