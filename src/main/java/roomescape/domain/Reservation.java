package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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

    public LocalTime getTime() {
        return time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        if (reservation.getName().isEmpty() || reservation.getDate() == null || reservation.getTime() == null) {
            throw new IllegalArgumentException ("필요한 인자가 없습니다!");
        }
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
