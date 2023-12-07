package roomescape.dto;

import roomescape.domain.Time;

public record ReservationResponse(Long id, String name, String date, Time time) {
}
