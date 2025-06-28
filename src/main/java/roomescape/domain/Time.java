package roomescape.domain;

import java.time.LocalTime;
import roomescape.exception.InvalidRequestException;

public class Time {
    private final Long id;
    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    private void validate(LocalTime time) {
        if (time == null) {
            throw new InvalidRequestException("시간은 비어있을 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
