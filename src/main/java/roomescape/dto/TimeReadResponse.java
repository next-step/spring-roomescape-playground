package roomescape.dto;

import java.util.List;
import roomescape.domain.Time;

public class TimeReadResponse {
    private final List<Time> times;

    private TimeReadResponse(List<Time> times) {
        this.times = times;
    }

    public static TimeReadResponse toDto(List<Time> times) {
        return new TimeReadResponse(times);
    }

    public List<Time> getLists() {
        return this.times;
    }
}
