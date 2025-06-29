package roomescape.controller.dto;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import roomescape.global.exception.InvalidValueException;

public record RequestTime(
        String time
) {

    public RequestTime {
        validateEmpty(time);
    }

    private void validateEmpty(final String time) {
        if (time == null || time.isBlank()) {
            throw new InvalidValueException("시간을 입력해 주세요.");
        }
    }

    public LocalTime parseTime() {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new InvalidValueException("시간(시:분)형식에 맞게 입력해 주세요. ex) 15:30");
        }
    }
}
