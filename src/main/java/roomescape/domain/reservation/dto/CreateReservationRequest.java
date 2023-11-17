package roomescape.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.model.Reservation;

public record CreateReservationRequest(
        @NotBlank(message = "이름을 입력해 주세요.")
        String name,
        @NotNull(message = "날짜를 입력해 주세요.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @NotNull(message = "시간을 입력해 주세요.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {

    public Reservation toEntity() {
        return Reservation.builder()
                .date(date)
                .name(name)
                .time(time)
                .build();
    }
}
