package hello.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hello.domain.Time;

import java.time.LocalTime;

public class TimeDto {

    private Long id;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public TimeDto() {
    }

    public TimeDto(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public static TimeDto toDto(Time time) {
        return new TimeDto(time.getId(), time.getTime());
    }
}
