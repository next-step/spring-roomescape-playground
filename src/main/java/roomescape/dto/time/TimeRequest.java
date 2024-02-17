package roomescape.dto.time;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;


public class TimeRequest {

    @NotNull
    private LocalTime time;

    public LocalTime getTime() {
        return time;
    }
}
