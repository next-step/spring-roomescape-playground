package roomescape.dto.request;

import java.util.Arrays;

import roomescape.exception.BadReservationSaveRequestException;

public record ReservationSaveRequest(
	String name,
	String date,
	String time
) {

	public ReservationSaveRequest {
		validate(name, date, time);
	}

	private void validate(String... fields) {
		boolean isBlank = Arrays.stream(fields).anyMatch(String::isBlank);
		if (isBlank) {
			throw new BadReservationSaveRequestException("예약 정보에 공백이 입력되었습니다.");
		}
	}
}
