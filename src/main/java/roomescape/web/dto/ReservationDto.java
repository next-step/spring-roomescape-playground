package roomescape.web.dto;

import lombok.Getter;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Getter
public class ReservationDto {



    private final Long id;
    private final String name;
    private final String date;
    private final Time time;


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


    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
    }
}