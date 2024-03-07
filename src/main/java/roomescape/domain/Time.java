package roomescape.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Time {
    private final Long id;
    private final LocalTime time;

    public Time(Long id, LocalTime time) {
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
        if (this == o)
            return true;

        if (!(o instanceof Time that))
            return false;

        return Objects.equals(id, that.id) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time);
    }
}
