package roomescape.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    public record CreateReservationDto(
            String name,

            @JsonFormat(pattern = "yyyy-MM-dd")
            LocalDate date,

            @JsonFormat(pattern = "HH:mm")
            LocalTime time
    ) {
        public Reservation toEntity() {
            return Reservation.builder()
                    .name(name)
                    .date(date)
                    .time(time)
                    .build();
        }
    }
}
