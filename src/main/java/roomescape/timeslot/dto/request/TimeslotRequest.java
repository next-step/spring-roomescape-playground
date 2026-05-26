package roomescape.timeslot.dto.request;

import java.time.LocalTime;

public record TimeslotRequest(
        LocalTime timeslot
) {
}
