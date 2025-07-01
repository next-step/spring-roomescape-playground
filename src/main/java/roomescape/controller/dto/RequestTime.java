package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class RequestTime {
    private final LocalTime time;

    @JsonCreator
    public RequestTime(@JsonProperty(value = "time") final String time) {
        validateEmpty(time);
        this.time = parseTime(time);
    }

    private void validateEmpty(final String time) {
        if (time == null || time.isBlank()) {
            throw new IllegalArgumentException("시간을 입력해 주세요.");
        }
    }

    private LocalTime parseTime(final String time) {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시간(시:분)형식에 맞게 입력해 주세요. ex) 15:30");
        }
    }

    public LocalTime getTime() {
        return time;
    }
}
