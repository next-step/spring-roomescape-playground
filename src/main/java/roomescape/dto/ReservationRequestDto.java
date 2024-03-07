package roomescape.dto;

import roomescape.domain.Time;

public record ReservationRequestDto(String name, String date, Long time) {
}
