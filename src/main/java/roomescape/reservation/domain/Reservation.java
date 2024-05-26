package roomescape.reservation.domain;

import java.time.LocalDateTime;

public class Reservation {

    private final String name;
    private final LocalDateTime reservationDateTime;
    private Long id;

    public Reservation(Long id, String name, LocalDateTime reservationDateTime) {
        this.id = id;
        this.name = name;
        this.reservationDateTime = reservationDateTime;
    }

    public Reservation(String name, LocalDateTime reservationDateTime) {
        this(null, name, reservationDateTime);
    }

    void setId(Long id) {
        this.id = id;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public LocalDateTime reservationDateTime() {
        return reservationDateTime;
    }
}
