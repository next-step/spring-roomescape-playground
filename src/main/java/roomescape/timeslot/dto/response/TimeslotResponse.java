package roomescape.timeslot.dto.response;

import java.time.LocalTime;

public record TimeslotResponse(
        Long id,
        LocalTime timeId
) {
}
