package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationId;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        ReservationId id,

        String name,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime time
) {
    public static ReservationResponse from(@Nonnull Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getTime().toLocalDate(),
                reservation.getTime().toLocalTime()
        );
    }
}
