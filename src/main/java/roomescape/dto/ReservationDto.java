package roomescape.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import roomescape.model.Reservation;

public record ReservationDto(
        @Nullable
        Long id,

        @NotBlank(message = "이름은 비어있으면 안됩니다")
        String name,

        @NotBlank(message = "날짜는 비어있으면 안됩니다")
        String date,

        @NotBlank(message = "시간은 비어있으면 안됩니다")
        String time) {

    public ReservationDto(Reservation reservation) {
        this(reservation.id(), reservation.name(), reservation.date(), reservation.time().id().toString());
    }
}
