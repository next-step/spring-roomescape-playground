package hello.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class Time {

    private Long id;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

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
}