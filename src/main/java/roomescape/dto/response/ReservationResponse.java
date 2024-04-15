package roomescape.dto.response;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final String date;
    private final Time time;

    private ReservationResponse(Long id, String name, String date, Time time) 
    private final String time;

    private ReservationResponse(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
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
    public String getTime() {
        return time;
    }
}