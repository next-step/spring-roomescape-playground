package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private final Long id;
    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        validateTime(time);

        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("시간을 입력해주세요.");
        }
    }
}
