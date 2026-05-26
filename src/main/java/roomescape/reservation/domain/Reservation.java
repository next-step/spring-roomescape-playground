package roomescape.reservation.domain;

import java.time.LocalDateTime;

public class Reservation {
    public static final int RESERVATION_LENGTH_MINUTES = 60;

    private Long id;
    private String name;
    private LocalDateTime dateTime;

    public Reservation(String name, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    private Reservation(Long id, String name, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Reservation withId(Long id) {
        return new Reservation(id, this.name, this.dateTime);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
