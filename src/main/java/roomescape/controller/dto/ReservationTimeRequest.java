package roomescape.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record ReservationTimeRequest(
        @NotNull(message = "시간은 비워둘 수 없습니다.")
        LocalTime time) {
}
