package roomescape.dto.timeDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import roomescape.model.Time;

import java.time.LocalTime;

public record TimeCreateRequest(
        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
    public Time toEntity() {
        return Time.from(this.time);
    }
}
