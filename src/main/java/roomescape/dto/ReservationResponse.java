package roomescape.dto;

import roomescape.domain.Reservation;

public class ReservationResponse {

    private final int id;
    private final String name;
    private final String date;
    private final TimeResponse time;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate().toString();
        this.time = new TimeResponse(reservation.getTime());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public TimeResponse getTime() {
        return time;
    }
}

