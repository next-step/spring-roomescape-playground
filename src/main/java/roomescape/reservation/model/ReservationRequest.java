package roomescape.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private final String name;
    private final String date;
    private final String time;


    public String getName() {
        return name;
    }


    public String getDate() {
        return date;
    }


    public String getTime() {
        return time;
    }

    public ReservationRequest( String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
