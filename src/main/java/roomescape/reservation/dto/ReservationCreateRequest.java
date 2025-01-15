package roomescape.reservation.dto;

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
        return new Reservation(id, name, date, time);
    }
}
