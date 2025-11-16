package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalTime;


public record ReservationCreateRequest(

        @NotBlank(message = "이름은 필수 항목입니다.")
        String name,

        @NotBlank(message = "날짜는 필수 항목입니다.")
        LocalDate date,

        @NotBlank(message = "시간은 필수 항목입니다.")
        LocalTime time
) {

}
