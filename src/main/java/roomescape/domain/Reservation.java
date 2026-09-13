package roomescape.domain;

import java.time.LocalDate;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    public Reservation(long id, String name, LocalDate date, Time time) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (date == null) {
            throw new IllegalArgumentException();
        }

        if (time == null) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.name = name;
        this.date = date;
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

    public Time getTime() {
        return time;
    }
}
