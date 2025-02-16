package roomescape.time.dto;

import java.time.LocalTime;

public class TimeCreateResponse {

    private Long id;

    private LocalTime time;

    public TimeCreateResponse(Long id, LocalTime time) {
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
