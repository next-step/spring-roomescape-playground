package roomescape.reservation.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import roomescape.reservation.domain.CreateReservationInfo;

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
    public CreateReservationInfo convertToDomain() {
        return new CreateReservationInfo(name, LocalDateTime.of(date, time));
    }
}
