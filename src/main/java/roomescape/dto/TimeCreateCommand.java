package roomescape.dto;

import java.time.LocalTime;

public record TimeCreateCommand(
        LocalTime startAt
) {
}
