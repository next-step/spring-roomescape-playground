package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.model.Reservation;


import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDto(
        Long id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime time
) {
    public ReservationResponseDto(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}

