package roomescape.dto.request;

import java.util.Arrays;
import java.util.regex.Pattern;

import roomescape.exception.BadReservationSaveRequestException;

public record ReservationSaveRequest(
	String name,
	String date,
	String time
) {

	private static final String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
	private static final String timeRegex = "\\d{2}:\\d{2}";

	public ReservationSaveRequest {
		validate(name, date, time);
	}

	private void validate(String name, String date, String time) {
		validateBlank(name, date, time);
		validateFormat(date, time);
	}

	private void validateBlank(String... fields) {
		boolean isBlank = Arrays.stream(fields).anyMatch(String::isBlank);
		if (isBlank) {
			throw new BadReservationSaveRequestException("예약 정보에 공백이 입력되었습니다.");
		}
	}

	private void validateFormat(String date, String time) {
		if (!Pattern.matches(dateRegex, date)) {
			throw new BadReservationSaveRequestException("날짜 형식이 올바르지 않습니다. (형식: yyyy-MM-dd)");
		}
		if (!Pattern.matches(timeRegex, time)) {
			throw new BadReservationSaveRequestException("시간 형식이 올바르지 않습니다. (형식: HH:mm)");
		}
	}
}
