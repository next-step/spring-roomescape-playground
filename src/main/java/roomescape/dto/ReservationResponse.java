package roomescape.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(Long id, String name, String date, String time) {
    public static ReservationResponse fromReservation(Reservation reservation) {
        String[] dateTime = localDateTimeToString(reservation.getDateTime()).split(" ");
        return new ReservationResponse(reservation.getId(), reservation.getName(), dateTime[0], dateTime[1]);
    }

    private static String localDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}
