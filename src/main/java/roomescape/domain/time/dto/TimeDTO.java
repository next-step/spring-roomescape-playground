package roomescape.domain.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

public record TimeDTO(
        @NotNull(message = "값을 입력해주세요.")
        @JsonFormat(pattern = "HH:mm")
        String time) {
}
