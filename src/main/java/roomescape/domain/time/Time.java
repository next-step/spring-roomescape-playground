package roomescape.domain.time;

import java.time.LocalTime;
import java.util.Objects;
import roomescape.common.error.ErrorCode;
import roomescape.common.error.exception.InvalidValueException;

public class Time {

    private Long id;

    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        if (Objects.isNull(time)) {
            throw new InvalidValueException(ErrorCode.INVALID_TIME_VALUE);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Time time = (Time) o;
        return Objects.equals(id, time.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
