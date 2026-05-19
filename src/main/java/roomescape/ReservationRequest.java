package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.exception.InvalidReservationException;

public class ReservationRequest {
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationRequest(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }

    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
}
