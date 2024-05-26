package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {

    public static ReservationTimeResponse from(final ReservationTime time) {
        return new ReservationTimeResponse(time.getId(), time.getTime());
    }

}
