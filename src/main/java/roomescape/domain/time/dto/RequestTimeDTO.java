package roomescape.domain.time.dto;

import roomescape.domain.time.Time;

public record RequestTimeDTO(String time) {
    public Time toTime() {
        return new Time(time);
    }
}
