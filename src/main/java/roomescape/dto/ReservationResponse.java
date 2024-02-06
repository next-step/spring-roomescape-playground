package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.Reservation;

public class ReservationResponse {
    private Long id;
    private String name;
    private String date;
    private LocalTime time;

    public ReservationResponse(final Long id, final String name, final String date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime().getTime();
    }
}
