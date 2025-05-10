package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public class ReservationRequest {

    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationRequest() {}

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Reservation makeValidReservation(int id) {
        return new Reservation(id, name, date, time);
    }
}
