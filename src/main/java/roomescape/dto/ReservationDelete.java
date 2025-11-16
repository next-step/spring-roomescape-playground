package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDelete (
        int id,
        String name,
        String date,
        String time
){
}
