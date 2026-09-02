package roomescape.dto;

import roomescape.domain.TimeSlot;
import roomescape.exception.InvalidTimeSlotException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public record TimeSlotRequest(
        String time
) {
    public TimeSlot toTimeSlot() {
        validate();
        try {
            return new TimeSlot(LocalTime.parse(time));
        } catch (DateTimeParseException e) {
            throw new InvalidTimeSlotException("시간 형식이 올바르지 않습니다. time=" + time);
        }
    }

    private void validate() {
        if (time == null || time.isBlank()) {
            throw new InvalidTimeSlotException("시간에 필요한 인자가 없습니다.");
        }
    }
}
