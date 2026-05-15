package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record ReservationRequest(
        @NotBlank(message = "이름은 필수 입력값입니다.")
        String name,

        @NotNull(message = "날짜는 필수 입력값입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotBlank(message = "시간은 필수 입력값입니다.")
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "시간은 HH:mm 형식(00:00 ~ 23:59)이어야 합니다.")
        String time
) {
}