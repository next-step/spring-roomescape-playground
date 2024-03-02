package roomescape.dto.reservation;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@NotEmpty
public class ReservationRequestDTO {
	public record AddReservationRequest(String name, String date, Long time_id) {
	}
}
