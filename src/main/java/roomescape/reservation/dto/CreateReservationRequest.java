package roomescape.reservation.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import roomescape.reservation.domain.CreateReservationInfo;
import roomescape.reservation.domain.ReservationException;
import roomescape.time.domain.TimeId;
import roomescape.time.domain.Times;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record CreateReservationRequest(
        @NotNull
        @Length(max = CreateReservationInfo.NAME_MAX_LENGTH)
        String name,

        @NotNull
        LocalDate date,

        @NotNull
        LocalTime time
) {
    public CreateReservationInfo convertToDomain(Times times) {
        TimeId timeId = times.getIdAt(time);
        if (timeId == null) {
            throw new ReservationException.TimeNotFound();
        }

        return new CreateReservationInfo(name, date, timeId);
    }
}
