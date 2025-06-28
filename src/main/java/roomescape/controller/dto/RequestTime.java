package roomescape.controller.dto;

import roomescape.global.exception.InvalidValueException;

public record RequestTime(
        String time
) {

    private static final String TIME_FORMAT_REGEX = "^([01]\\d|2[0-3]):[0-5]\\d$";

    public RequestTime {
        validateFormat(time);
    }

    private void validateFormat(final String time) {
        if (time == null || !time.matches(TIME_FORMAT_REGEX)) {
            throw new InvalidValueException("시간(시:분)형식에 맞게 입력해 주세요. ex) 15:30");
        }
    }
}
