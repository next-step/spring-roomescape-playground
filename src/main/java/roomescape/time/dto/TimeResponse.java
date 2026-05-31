package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import roomescape.time.domain.Time;
import roomescape.time.domain.TimeId;

import java.time.LocalTime;

public record TimeResponse(
        @Nonnull
        TimeId id,

        @Nonnull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime time
) {
    public static TimeResponse from(Time time) {
        return new TimeResponse(time.getId(), time.getTime());
    }
}
