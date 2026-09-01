package roomescape.domain;

import roomescape.exception.TimeErrorCode;
import roomescape.exception.TimeException;

import java.time.LocalTime;

public class Time {
    private final Long id;

    private final LocalTime startAt;

    public Time(Long id, LocalTime startAt) {
        validateStartAt(startAt);

        this.id = id;
        this.startAt = startAt;
    }

    public Time(LocalTime startAt) {
        this(null, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    private void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new TimeException(TimeErrorCode.TIME_INVALID);
        }
    }
}
