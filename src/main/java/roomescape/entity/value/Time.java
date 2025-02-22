package roomescape.entity.value;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import roomescape.exception.InvalidInputException;

public class Time {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private Long id;

    private final LocalTime time;

    private Time() {
        this.id = null;
        this.time = null;
    }

    public Time(Long id, String value) {
        this.id = id;
        this.time = parseTime(value);
    }

    private LocalTime parseTime(String time) {
        validateTime(time);
        try {
            return LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("time값이 HH:mm 형식이 아닙니다.");
        }
    }


    private static void validateTime(String time) {
        if (time == null || time.isBlank()) {
            throw new InvalidInputException("time은 null이거나 공백이 들어갈 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time.format(formatter);
    }

}
