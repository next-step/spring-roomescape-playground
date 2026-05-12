package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationRequest(
        @NotBlank(message = "이름이 비어 있을 수 없습니다.")
        String name,

        @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
        @NotNull(message = "예약 날짜가 비어 있을 수 없습니다.")
        LocalDate date,

        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm")
        @NotNull(message = "예약 시간이 비어 있을 수 없습니다.")
        LocalTime time) {
    public Reservation toReservation() {
        return new Reservation(name, LocalDateTime.of(date, time));
    }
}
