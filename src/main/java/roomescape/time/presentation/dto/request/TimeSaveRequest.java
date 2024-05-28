package roomescape.time.presentation.dto.request;

import java.util.regex.Pattern;

import roomescape.time.presentation.exception.BadTimeSaveRequestException;

public record TimeSaveRequest(
	String time
) {

	private static final String timeRegex = "\\d{2}:\\d{2}";

	public TimeSaveRequest {
		validate(time);
	}

	private void validate(String time) {
		validateBlank(time);
		validateFormat(time);
	}

	private void validateBlank(String time) {
		if (time.isBlank()) {
			throw new BadTimeSaveRequestException("시간은 공백일 수 없습니다.");
		}
	}

	private void validateFormat(String time) {
		if (!Pattern.matches(timeRegex, time)) {
			throw new BadTimeSaveRequestException("시간 형식이 올바르지 않습니다.");
		}
	}
}
