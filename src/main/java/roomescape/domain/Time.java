package roomescape.domain;

import roomescape.exception.InvalidTimeException;

import java.time.LocalTime;

public class Time {

    private final Long id;
    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        if (time == null) {
            throw new InvalidTimeException("시간을 입력해야 합니다.");
        }

        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
