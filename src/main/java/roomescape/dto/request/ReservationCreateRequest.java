package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(String name, LocalDate date, LocalTime time) {

}
