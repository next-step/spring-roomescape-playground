package roomescape.domain;

import java.time.LocalDate;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate reservedDate;
    private Time time;

    private Reservation(Long id, String name, LocalDate reservedDate, Time time) {
        this.id = id;
        this.name = name;
        this.reservedDate = reservedDate;
        this.time = time;
    }

    public Reservation(String name, LocalDate reservedDate, Time time) {
        this(null, name, reservedDate, time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public Time getTime() {
        return time;
    }

    public Reservation withId(Long id) {
        return new Reservation(id, name, reservedDate, time);
    }
}
