package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public class ReservationResponseForm {

    private final Long id;
    private final String name;
    private final String date;
    private final Time time;

    private ReservationResponseForm(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseForm from(Reservation reservation) {
        return new ReservationResponseForm(reservation.getId(), reservation.getName(), reservation.getDate(),
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
        return time;
    }
}
