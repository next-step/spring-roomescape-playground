package roomescape.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public Reservation(Long id, String name, String date, String time) {
        validateInput(id, name, date, time);
        this.id = id;
        this.name = name;
        this.date = parseDate(date);
        this.time = parseTime(time);
    }

    private void validateInput(Long id, String name, String date, String time) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("[Error] id는 null이거나 음수일 수 없습니다.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("[Error] name는 null이거나 공백이 들어갈 수 없습니다.");
        }

        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("[Error] date는 null이거나 공백이 들어갈 수 없습니다.");
        }

        if (time == null || time.trim().isEmpty()) {
            throw new IllegalArgumentException("[Error] time는 null이거나 공백이 들어갈 수 없습니다.");
        }
    }

    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[Error] date는 yyyy-MM-dd 형식이 아닙니다.");
        }
    }

    private LocalTime parseTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[Error] time는 HH:mm 형식이 아닙니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

}
