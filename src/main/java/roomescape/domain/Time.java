package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalTime;
import java.util.Objects;

public class Time {
    private Long id;
    private LocalTime time;

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Time(LocalTime time) {
        this(null, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time anotherTime = (Time) o;
        return Objects.equals(id, anotherTime.id) && Objects.equals(time, anotherTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time);
    }
}
