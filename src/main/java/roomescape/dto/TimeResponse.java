package roomescape.dto;

import lombok.Getter;
import roomescape.domain.Time;

@Getter
public class TimeResponse {

    private final Long id;
    private final String time;

    public TimeResponse(Time time) {
        this.id = time.getId();
        this.time = time.getTime();
    }
}
