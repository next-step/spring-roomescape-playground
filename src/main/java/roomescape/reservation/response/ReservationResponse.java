package roomescape.reservation.response;

import java.time.format.DateTimeFormatter;
import roomescape.reservation.model.Reservation;

public class ReservationResponse {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate().format(DATE_FORMATTER);
        this.time = reservation.getTime().format(TIME_FORMATTER);
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

