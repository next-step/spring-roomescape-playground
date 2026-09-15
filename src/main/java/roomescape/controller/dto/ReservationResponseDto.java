package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.model.Reservation;


import java.time.LocalDate;


public record ReservationResponseDto(
        Long id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        TimeResponseDto time
) {
    public ReservationResponseDto(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new TimeResponseDto(reservation.getTime().getId(), reservation.getTime().getTime())
        );
    }
}

