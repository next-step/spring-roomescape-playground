package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public class Time {
    private Long id;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public Time() {
    }

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Time(LocalTime time) {
        this(null, time);
    }

    public static Time toEntity(Time time, Long id) {
        return new Time(id, time.getTime());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
