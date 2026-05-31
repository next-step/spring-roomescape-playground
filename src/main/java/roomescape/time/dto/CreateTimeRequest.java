package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import roomescape.time.domain.CreateTimeInfo;

import java.time.LocalTime;

public record CreateTimeRequest(
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime time
) {
    public CreateTimeInfo convertToDomain() {
        return new CreateTimeInfo(time);
    }
}
