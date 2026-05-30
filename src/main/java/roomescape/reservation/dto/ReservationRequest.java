package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationRequest(
        @NotBlank(message = "이름이 비어 있을 수 없습니다.")
        String name,

        @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
        @NotNull(message = "예약 날짜가 비어 있을 수 없습니다.")
        LocalDate date,

        @NotNull(message = "예약 시간이 비어 있을 수 없습니다.")
        Long time) {
}
