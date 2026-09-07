package roomescape.dto;

import roomescape.domain.Time;
import roomescape.exception.InvalidTimeException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public record TimeRequest(
        String time
) {
    public Time toTime() {
        validate();
        try {
            return new Time(LocalTime.parse(time));
        } catch (DateTimeParseException e) {
            throw new InvalidTimeException("시간 형식이 올바르지 않습니다. time=" + time);
        }
    }

    private void validate() {
        if (time == null || time.isBlank()) {
            throw new InvalidTimeException("시간에 필요한 인자가 없습니다.");
        }
    }
}
