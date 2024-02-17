package roomescape.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationResponseDTO {
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class QueryReservation {
		private Long id;
		private String name;
		private String date;
		private String time;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AddReservation {
		private Long id;
		private String name;
		private String date;
		private String time;

	}
}