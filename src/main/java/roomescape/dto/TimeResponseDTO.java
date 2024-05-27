package roomescape.dto;

import roomescape.domain.Time;
import java.time.LocalTime;

public class TimeResponseDTO {

    private Long id;
    private LocalTime time;

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