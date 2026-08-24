package com.cholog.roomescape.roomescape.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.cholog.roomescape.roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(

        @NotBlank(message = "이름 필드는 비어있을 수 없습니다.")
        @Size(max = 20, message = "이름은 20자를 넘길 수 없습니다.")
        String name,

        @NotNull(message = "예약 날짜는 비어있을 수 없습니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull(message = "예약 시각은 비어있을 수 없습니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
        public static Reservation toReservationWithoutId(ReservationRequest request) {
            return new Reservation(request.name, request.date, request.time);
        }
}
