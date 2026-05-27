package roomescape.time.dto;

import roomescape.time.domain.Time;

public record TimeResponse(Long id, String time) {
    public static TimeResponse fromTime(Time time) {
        return new TimeResponse(time.getId(), time.getTime().toString());
    }
}
