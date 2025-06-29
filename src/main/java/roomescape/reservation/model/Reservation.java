package roomescape.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Long timeId;
    private final LocalTime timeValue;

    public Reservation(Long id, String name, LocalDate date, Long timeId, LocalTime timeValue) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.timeValue = timeValue;
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

    public Long getTimeId() {
        return timeId;
    }

    public LocalTime getTimeValue() {
        return timeValue;
    }
}
