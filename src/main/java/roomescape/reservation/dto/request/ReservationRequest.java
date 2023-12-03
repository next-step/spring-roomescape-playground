package roomescape.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    public record CreateReservationDto(

            @NotBlank(message = "이름을 입력해주세요.")
            String name,

            @NotNull(message = "날짜를 입력해주세요.")
            @JsonFormat(pattern = "yyyy-MM-dd")
            LocalDate date,

            @NotNull(message = "시간을 입력해주세요.")
            @JsonFormat(pattern = "HH:mm")
            Time time
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
