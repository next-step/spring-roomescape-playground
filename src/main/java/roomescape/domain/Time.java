package roomescape.domain;

import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.exception.NoParameterException;

public class Time {
    private Long id;
    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime time;

    public Time(final LocalTime time) { this.time = time; }

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
        checkValidity();
    }

    private void checkValidity() {
        if (this.getTime() == null) throw new NoParameterException();
    }

    public Long getId() { return id; }
    public LocalTime getTime() { return time; }

    public static Time toEntity(Time time, Long id) {
        return new Time(id, time.time);
    }
}