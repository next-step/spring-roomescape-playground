package roomescape.dto.reservationDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;


public record ReservationCreateRequest(

        @NotBlank(message = "이름은 필수 항목입니다.")
        String name,

        @NotNull(message = "날짜는 필수 항목입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull
        Long timeId
) {

}
