package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
