package roomescape.domain.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Reservation {
    private static AtomicLong INDEX = new AtomicLong(0);

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    @Builder
    private Reservation(String name, LocalDate date, LocalTime time) {
        this.id = INDEX.incrementAndGet();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isEquals(long id) {
        return this.id == id;
    }
}
