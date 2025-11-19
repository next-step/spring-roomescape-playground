package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;


public record ReservationCreateRequest(

        @NotBlank(message = "이름은 필수 항목입니다.")
        String name,

        @NotNull(message = "날짜는 필수 항목입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull(message = "시간은 필수 항목입니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
    public Reservation toEntity() {
        return new Reservation(name, date, time);
    }
}


