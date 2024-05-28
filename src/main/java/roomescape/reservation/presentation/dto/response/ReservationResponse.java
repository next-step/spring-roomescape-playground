package roomescape.reservation.presentation.dto.response;

import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

public record ReservationResponse(
	Long id,
	String name,
	String date,
	Time time
) {

	public static ReservationResponse from(Reservation reservation) {
		return new ReservationResponse(
			reservation.getId(),
			reservation.getName(),
			reservation.getDate(),
			reservation.getTime());
	}
}
