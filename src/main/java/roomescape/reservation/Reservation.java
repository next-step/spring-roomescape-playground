package roomescape.reservation;

import roomescape.exception.ReservationValidationException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    
    private final Long id;
    
    private final String name;
    
    private final LocalDate date;
    
    private final LocalTime time;

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || name.isEmpty()) {
            throw new ReservationValidationException();
        }

        if (date == null) {
            throw new ReservationValidationException();
        }

        if (time == null) {
            throw new ReservationValidationException();
        }
    }

    public static Reservation ofNew(String name, LocalDate date, LocalTime time) {
        return new Reservation(null, name, date, time);
    }
    
    public static Reservation ofExist(Long id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
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
}
