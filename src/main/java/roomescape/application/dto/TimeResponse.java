package roomescape.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class TimeResponse {
    private final Long id;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public TimeResponse(final Long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public static TimeResponse from(final Time time) {
        return new TimeResponse(time.getId(), time.getTime());
    }

    public static List<TimeResponse> from(final List<Time> times) {
        return times.stream()
                .map(time -> new TimeResponse(time.getId(), time.getTime()))
                .collect(Collectors.toList());
    }
}
