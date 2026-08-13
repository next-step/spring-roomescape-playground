package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime localTime;

    public Reservation(Long id, String name, LocalDate date, LocalTime localTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.localTime = localTime;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
