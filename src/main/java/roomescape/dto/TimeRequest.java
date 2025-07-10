package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record TimeRequest(
    @NotNull(message = "{time.time.notNull}")
    LocalTime time
) {
}
