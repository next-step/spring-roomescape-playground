package roomescape.domain.reservationTime.dto;

import java.time.LocalTime;
import roomescape.domain.reservationTime.domain.ReservationTime;
import roomescape.global.exception.RoomescapeBadRequestException;

public record ReservationTimeRequest(LocalTime time) {

    public ReservationTimeRequest {
        if (time == null) {
            throw new RoomescapeBadRequestException("잘못된 시간 정보입니다.");
        }
    }

    public ReservationTime toReservationTime() {
        return new ReservationTime(time);
    }
}
