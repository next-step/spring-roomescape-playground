package roomescape.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public class Time {
    private Long id;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}