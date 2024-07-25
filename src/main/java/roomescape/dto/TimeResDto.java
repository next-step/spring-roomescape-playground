package roomescape.dto;

import roomescape.domain.Time;

public record TimeResDto(
        int id, String time
) {
    public static TimeResDto from(Time time) {
        return new TimeResDto(time.getId(), time.getTime());
    }
}
