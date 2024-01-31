package roomescape.domain.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class TimeAddRequest {
    private Long id;
    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime time;

    public TimeAddRequest(Long id, LocalTime time) {
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
