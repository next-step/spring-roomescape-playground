package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        long id,

        String name,

        LocalDate date,

        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
    public static ReservationResponse create(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
