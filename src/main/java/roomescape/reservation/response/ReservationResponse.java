package roomescape.reservation.response;

import java.time.format.DateTimeFormatter;
import roomescape.reservation.model.Reservation;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.time = reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}

