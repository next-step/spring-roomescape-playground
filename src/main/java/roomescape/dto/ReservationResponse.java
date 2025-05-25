package roomescape.dto;

import lombok.Getter;
import roomescape.domain.Reservation;

@Getter
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
}

