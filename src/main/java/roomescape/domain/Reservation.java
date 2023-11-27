package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;


public class Reservation {

    private final AtomicLong id = new AtomicLong(1);
    String name;
    LocalDate date;
    String time;

    public Reservation(String name) {
        id.incrementAndGet();
        this.name = name;
        this.date = LocalDate.now();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public AtomicLong getId() {
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
