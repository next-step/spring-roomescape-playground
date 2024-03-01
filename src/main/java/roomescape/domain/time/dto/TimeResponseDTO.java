package roomescape.domain.time.dto;

import java.time.LocalTime;
import java.util.Timer;

public class TimeResponseDTO {
    private Long id;
    private LocalTime time;

    TimeResponseDTO() {

    }

    public TimeResponseDTO(Long id, LocalTime time) {
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
