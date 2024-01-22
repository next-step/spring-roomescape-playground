package roomescape.domain;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final String time;

    public Reservation(String name, LocalDate date, String time) {
        this.id = null;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, LocalDate date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation with(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
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

    public String getTime() {
        return time;
    }
}
