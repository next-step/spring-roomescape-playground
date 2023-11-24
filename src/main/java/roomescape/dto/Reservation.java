package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Reservation {
    private final AtomicLong idGenerator = new AtomicLong();
    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {
        this.id = idGenerator.incrementAndGet();
    }


    public Reservation(String name, LocalDate localDate, LocalTime localTime) {
        this.id = idGenerator.incrementAndGet();
        this.name = name;
        this.date = localDate;
        this.time = localTime;
    }

    public long getId() {
        return id;
    }

}
