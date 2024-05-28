package roomescape.reservation.presentation.dto.request;

import java.util.Arrays;
import java.util.regex.Pattern;

import roomescape.reservation.presentation.exception.BadReservationSaveRequestException;

public record ReservationSaveRequest(
	String name,
	String date,
	Long timeId
) {

	private static final String dateRegax = "\\d{4}-\\d{2}-\\d{2}";

	public ReservationSaveRequest {
		validate(name, date, timeId);
	}

	private void validate(String name, String date, Long timeId) {
		validateBlank(name, date);
		validateFormat(date);
		validateType(timeId);
	}

	private void validateBlank(String... fields) {
		boolean isBlank = Arrays.stream(fields).anyMatch(String::isBlank);
		if (isBlank) {
			throw new BadReservationSaveRequestException("예약 정보에 공백이 입력되었습니다.");
		}
	}

	private void validateFormat(String date) {
		if (!Pattern.matches(dateRegax, date)) {
			throw new BadReservationSaveRequestException("날짜 형식이 올바르지 않습니다.");
		}
	}

	private void validateType(Long timeId) {
		if (timeId == null) {
			throw new BadReservationSaveRequestException("시간 정보는 id를 입력해주세요.");
		}
	}
}
