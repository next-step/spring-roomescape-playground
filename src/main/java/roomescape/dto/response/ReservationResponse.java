package roomescape.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {

}
