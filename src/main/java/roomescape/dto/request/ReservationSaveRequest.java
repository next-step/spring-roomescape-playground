package roomescape.dto.request;

import java.util.Arrays;

import roomescape.exception.BadReservationSaveRequestException;

public record ReservationSaveRequest(
	String name,
	String date,
	String time
) {

	public ReservationSaveRequest {
		validate();
	}

	private void validate() {
		boolean isBlank = Arrays.stream(new String[] {name, date, time}).anyMatch(String::isBlank);
		if (isBlank) {
			throw new BadReservationSaveRequestException("예약 정보에 공백이 입력되었습니다.");
		}
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}
}
