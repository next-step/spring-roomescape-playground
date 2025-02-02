package roomescape.reservation.dto;

import roomescape.exception.ReservationValidationException;
import roomescape.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateRequest {
    
    private String name;
    
    private String date;
    
    private String time;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
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
