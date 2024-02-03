package roomescape.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public record TimeRequest(
        @NotBlank(message = "시간을 입력해주세요")
        @DateTimeFormat(pattern = "hh:MM")
        String time
) {
}
