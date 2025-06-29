package roomescape.reservation.request;

import java.time.LocalDate;

public class ReservationRequest {

    public LocalDate date;
    public String name;
    public String time;

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTimeId() {
        return time;
    }
}
