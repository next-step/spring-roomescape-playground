package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.reservation.domain.Reservation;

public record ReservationResponse(Long id, String name, LocalDate date, String time) {

    public static ReservationResponse fromReservation(final Reservation reservation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                formatter.format(reservation.getTime()));
    }
}
