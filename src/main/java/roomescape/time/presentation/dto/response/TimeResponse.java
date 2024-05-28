package roomescape.time.presentation.dto.response;

import roomescape.time.domain.Time;

public record TimeResponse(
	Long id,
	String time
) {

	public static TimeResponse from(Time time) {
		return new TimeResponse(
			time.getId(),
			time.getTime()
		);
	}
}
