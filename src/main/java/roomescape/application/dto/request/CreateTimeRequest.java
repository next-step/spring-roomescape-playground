package roomescape.application.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record CreateTimeRequest(
        @NotNull LocalTime time
) {
}
