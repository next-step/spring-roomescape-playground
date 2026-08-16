package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotNull(message = "예약 날짜는 비어 있을 수 없습니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotBlank(message = "예약자 이름은 비어 있을 수 없습니다.")
        @Pattern(
                regexp = "^\\s*$|^[가-힣a-zA-Z]+( [가-힣a-zA-Z]+)*$",
                message = "예약자 이름 형식이 올바르지 않습니다."
        )
        String name,

        @NotNull(message = "예약 시간은 비어 있을 수 없습니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
}
