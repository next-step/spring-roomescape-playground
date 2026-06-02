package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record ReservationCreateRequest(
        @NotBlank(message = "이름은 비어 있을 수 없습니다.")
        @Size(max = 255, message = "이름은 255자를 넘을 수 없습니다.")
        String name,

        @NotNull(message = "날짜는 필수입니다.")
        String date,

        @JsonAlias("time")
        @NotNull(message = "시간은 필수입니다.")
        Long timeId
) { }
