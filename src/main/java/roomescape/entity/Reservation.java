package roomescape.entity;

import java.time.LocalDate;
import roomescape.exception.InvalidException;

public class Reservation {

    private final long id;

    private final String name;

    private final LocalDate date;

    private final Time time;

    public Reservation(long id, String name, LocalDate date, Time time) {
        validateName(name);
        validateDate(date);
        validateTime(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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

    private void validateTime(Time time) {
        if (time == null) {
            throw new InvalidException("Time is required");
        }
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

    public Time getTime() {
        return time;
    }


}



