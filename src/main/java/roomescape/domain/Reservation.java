package roomescape.domain;

import java.time.LocalDateTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDateTime reservedAt;

    private Reservation(Long id, String name, LocalDateTime reservedAt) {
        this.id = id;
        this.name = name;
        this.reservedAt = reservedAt;
    }

    public Reservation(String name, LocalDateTime reservedAt) {
        this(null, name, reservedAt);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }

    public Reservation withId(Long id) {
        return new Reservation(id, name, reservedAt);
    }
}
