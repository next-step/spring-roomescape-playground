package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationCreateRequest {
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotNull(message = "날짜은 필수 입력값입니다.")
    private LocalDate date;

    @NotNull(message = "시간은 필수 입력값입니다.")
    private LocalTime time;
}
