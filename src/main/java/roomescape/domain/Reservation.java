package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
