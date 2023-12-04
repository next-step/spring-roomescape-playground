package roomescape.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationDTO(
        @NotBlank(message = "값을 입력해주세요.")
        String name,

        @NotNull(message = "값을 입력해주세요.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull(message = "값을 입력해주세요.")
        Long timeId) {
}
