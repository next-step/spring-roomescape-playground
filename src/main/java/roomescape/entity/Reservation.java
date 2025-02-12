package roomescape.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.exception.InvalidException;

public class Reservation {

    private long id;
    private String name;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
        validateName(name);
        validateDate(date);
        validateTime(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(){}

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



