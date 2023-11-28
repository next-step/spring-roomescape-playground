package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;

@Getter
public class Reservation {
    private long id = 0;
    private String name;
    private LocalDate date;

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private LocalTime time;

    public long getId() {
        return id;
    }

}
