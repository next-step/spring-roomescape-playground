package roomescape.time.dto.response;

import roomescape.time.domain.Time;

import java.time.LocalTime;

public class TimeResponse {
    public record createTimeDto(
            Long id,
            LocalTime time
    ) {
        public createTimeDto(Time time) {
            this(
                    time.getId(),
                    time.getTime()
            );
        }
    }
}
