package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class TimeRequest {

    @NotNull
    private final LocalTime time;

    @JsonCreator
    public TimeRequest(@JsonProperty("time") LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
