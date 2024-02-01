package roomescape.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import roomescape.model.entity.Time;

import java.time.LocalTime;

public record TimeDto (
        Long id,
        @NotNull @JsonFormat(pattern = "HH:mm") LocalTime time
)
{
    public Time toEntity() {
        return new Time(id, time);
    }
}