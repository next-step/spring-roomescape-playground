package roomescape.reservation.dto;

import roomescape.time.dto.TimeResponse;

public record ReservationResponse(Long id, String name, String date, TimeResponse time) {
}
