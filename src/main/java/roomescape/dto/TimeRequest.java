package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record TimeRequest(@NotNull LocalTime time) {

    public TimeRequest(@JsonProperty("time") LocalTime time) {
        this.time = time;
    }
}
