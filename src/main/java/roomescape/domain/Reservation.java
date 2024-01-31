package roomescape.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Reservation {
    private final Long id;
    private final String name;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private final LocalDate date;
    private final Time time;

    public Reservation(final Long id, final String name, final LocalDate date, final Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final LocalDate date, final Time time) {
        this(null, name, date, time);
    }

    public static Reservation toEntity(final Long id, final Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
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

