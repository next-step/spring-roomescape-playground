package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public class ReservationResponse {
    private Long id;
    private String name;
    private String date;
    private Time time;

    public ReservationResponse(final Long id, final String name, final String date, final Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
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

    public Time getTime() {
        return time;
    }
}
