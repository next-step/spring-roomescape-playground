package roomescape.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationRequestDTO {
  	public record AddReservationRequest(@NotEmpty String name, @NotEmpty String date, @NotEmpty String time) {}
	}
}
