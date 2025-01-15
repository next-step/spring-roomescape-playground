package roomescape.reservation.dto;

import roomescape.exception.ReservationValidationException;
import roomescape.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateRequest {
    
    private String name;
    
    private LocalDate date;
    
    private LocalTime time;

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
    
    public Reservation toEntity(Long id) {
        if (name == null || name.isEmpty()) {
            throw new ReservationValidationException();
        }

        if (date == null) {
            throw new ReservationValidationException();
        }

        if (time == null) {
            throw new ReservationValidationException();
        }
        
        return new Reservation(id, name, date, time);
    }
}
