package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record TimeRequest(
        @NotNull(message = "시간은 필수입니다.") LocalTime time
) {

    public ReservationTime toReservationTime() {
        return ReservationTime.create(time);
    }
}
