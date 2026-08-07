package roomescape;

import java.time.format.DateTimeFormatter;

public record ReservationResponse(
    Long id,
    String name,
    String date,
    String time
) {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().format(DATE_FORMATTER),
                reservation.getTime().format(TIME_FORMATTER)
        );
    }
}

