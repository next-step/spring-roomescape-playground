package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationRequest () {};

    public ReservationRequest(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
}
