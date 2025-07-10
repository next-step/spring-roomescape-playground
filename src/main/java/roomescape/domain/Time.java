package roomescape.domain;

import java.time.LocalTime;
import roomescape.exception.InvalidRequestException;

public class Time {
    private final Long id;
    private final LocalTime time;
    private final boolean isDeleted;

    public Time(Long id, LocalTime time) {
        this(id, time, false);
    }

    public Time(Long id, LocalTime time, boolean isDeleted) {
        validate(time);
        this.id = id;
        this.time = time;
        this.isDeleted = isDeleted;
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

    public boolean isDeleted() {
        return isDeleted;
    }
}
