package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

}
