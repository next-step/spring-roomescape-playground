package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import roomescape.model.Reservation;


public record ReservationCreateRequest(

        @NotBlank(message = "이름은 필수 항목입니다.")
        String name,

        @NotBlank(message = "날짜는 필수 항목입니다.")
        String date,

        @NotBlank(message = "시간은 필수 항목입니다.")
        String time
) {
    public Reservation toEntity() {
        return Reservation.create(name, date, time);
    }
}
