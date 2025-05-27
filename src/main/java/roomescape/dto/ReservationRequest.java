package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        LocalDate date,
        String name,
        LocalTime time
) {
    public static ReservationRequest of(String date, String name, String time) {
        return new ReservationRequest(
                LocalDate.parse(date),
                name,
                LocalTime.parse(time)
        );
    }
}
