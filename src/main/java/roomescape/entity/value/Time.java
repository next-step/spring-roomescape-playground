package roomescape.entity.value;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import roomescape.exception.InvalidInputException;

public class Time {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalTime value;


    private Time(String value) {
        validateInput(value);
        this.value = parseTime(value);
    }

    public static Time of(String value) {
        return new Time(value);
    }

    private static void validateInput(String time) {
        if (time == null || time.isBlank()) {
            throw new InvalidInputException("time는 null이거나 공백이 들어갈 수 없습니다.");
        }
    }

    private LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("time는 HH:mm 형식이 아닙니다.");
        }
    }

    public String getValue() {
        return value.format(formatter);
    }

}
