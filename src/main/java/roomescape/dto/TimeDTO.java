package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public class TimeDTO {

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    public TimeDTO() {}

    public TimeDTO(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
