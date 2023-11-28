package roomescape.domain;

import roomescape.dto.request.ReservationRequest;

public class Reservation {

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toDomain(long id, ReservationRequest reservationRequest) {
        return new Reservation(id, reservationRequest.getName(), reservationRequest.getDate(), reservationRequest.getTime());
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
