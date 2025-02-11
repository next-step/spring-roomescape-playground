package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.domain.reservation.domain.Reservation;

public record ReservationResponse(Long id, String name, LocalDate date, String time) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponse fromReservation(final Reservation reservation) {

        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                TIME_FORMATTER.format(reservation.getTime()));
    }
}
