package roomescape.domain.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.time.entity.Time;

public record ReservationCreateRequestDto(@NotBlank(message = "예약자명이 공백입니다.") String name,
                                          @NotBlank(message = "예약일이 공백입니다.") String date,
                                          @NotBlank(message = "예약시간이 공백입니다.") String timeId) {
    public Reservation toEntity(Long id, Time time) {
        return new Reservation(id, name, date, time);
    }
}