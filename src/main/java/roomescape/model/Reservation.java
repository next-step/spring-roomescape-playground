package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    Long id;
    String name;
    private LocalDate date;
    private Time time;

    private Reservation(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(String name, LocalDate date, Time time) {
        return new Reservation(null, name, date, time);
    }

    public static Reservation of(Long id, String name, LocalDate date, Time time) {
        return new Reservation(id, name, date, time);
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

    public Time getTime() {
        return time;
    }
}
