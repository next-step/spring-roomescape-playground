package roomescape.dto.reservation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationResponseDTO {
	public record QueryReservationResponse(Long id, String name, String date, String time_value) {
	}

	public record AddReservationResponse(Long id, String name, String date, String time_value) {
	}
}