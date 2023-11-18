package roomescape.domain.reservation.entity;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Reservation {

    private static AtomicLong pk = new AtomicLong(1);

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.id = pk.getAndIncrement();
        this.name = name;
        this.date = date;
        this.time = time;
    }

}
