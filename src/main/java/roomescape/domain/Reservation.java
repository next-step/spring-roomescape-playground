package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;


public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final String time;

    public Reservation(Long id, String name) {
        this.id = id;
        this.name = name;
        this.date = LocalDate.now();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
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
