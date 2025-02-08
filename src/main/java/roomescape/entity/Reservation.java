package roomescape.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.exception.InvalidException;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        validateName(name);
        this.name = name;
        validateDate(date);
        this.date = date;
        validateTime(time);
        this.time = time;
    }

    public long getId() {
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

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidException("Name is required");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new InvalidException("Date is required");
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new InvalidException("Time is required");
        }
    }
}

