package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimeResponse {
    private Long id;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public TimeResponse(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static TimeResponse from(Time time) {
        return new TimeResponse(
                time.getId(),
                time.getTime()
        );
    }

    public Long getId() { return id; }
    public LocalTime getTime() { return time; }
}

