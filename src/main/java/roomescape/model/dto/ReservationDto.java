package roomescape.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import roomescape.model.entity.Reservation;
import roomescape.model.entity.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(
        Long id,
        @NotEmpty String name,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @NotNull @JsonProperty(value="time") Long timeId
)
{
    public Reservation toEntity(Time time) {
        return new Reservation(id, name, date, time);
    }
}
