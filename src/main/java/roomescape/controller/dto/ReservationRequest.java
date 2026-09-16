package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationRequest(
        @NotBlank(message = "예약자 이름은 비워둘 수 없습니다.")
        String name,

        @NotNull(message = "예약 날짜는 비워둘 수 없습니다.")
        LocalDate date,

        @JsonProperty("time")
        @NotNull(message = "예약 시간은 비워둘 수 없습니다.")
        Long timeId) {
}
