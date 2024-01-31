package roomescape.domain;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public class Time {

    private Long id;
    @DateTimeFormat(pattern = "HH:MM")
    @NotNull
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
