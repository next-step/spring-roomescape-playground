package roomescape.entity.value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import roomescape.exception.InvalidInputException;

public class Date {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LocalDate value;


    private Date(String value) {
        validateInput(value);
        this.value = parseDate(value);
    }

    public static Date of(String value) {
        return new Date(value);
    }

    private static void validateInput(String date) {
        if (date == null || date.isBlank()) {
            throw new InvalidInputException("date는 null이거나 공백이 들어갈 수 없습니다.");
        }
    }


    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("date는 yyyy-MM-dd 형식이 아닙니다.");
        }
    }

    public String getValue() {
        return value.format(formatter);
    }
}
