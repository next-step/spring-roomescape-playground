package roomescape.reservation.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    public LocalDate date;
    public String name;
    public LocalTime time;

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }
}
