package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class Reservation {
    private Long id;
    private String name;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
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

    public LocalTime getTime() {
        return time;
    }

    public void update(Reservation reservation) {
        this.name = reservation.name;
        this.date = reservation.date;
        this.time = reservation.time;
    }
}

