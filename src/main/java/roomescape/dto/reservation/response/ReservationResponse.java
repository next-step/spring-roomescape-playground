package roomescape.dto.reservation.response;

import java.time.LocalDate;
import roomescape.domain.Time;

public record ReservationResponse(Long id, String name, LocalDate date, Time time) {

}
