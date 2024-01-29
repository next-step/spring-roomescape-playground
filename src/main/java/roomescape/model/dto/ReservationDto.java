package roomescape.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import roomescape.model.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(
        Long id,
        @NotEmpty String name,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @NotNull @JsonFormat(pattern = "HH:mm") LocalTime time
)
{
    public Reservation toEntity() {
        return new Reservation(id, name, date, time);
    }
}
