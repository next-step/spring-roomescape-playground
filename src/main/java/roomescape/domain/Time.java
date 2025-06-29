package roomescape.domain;

import java.time.LocalTime;

public record Time(
        Long id,
        LocalTime time
) {
}
