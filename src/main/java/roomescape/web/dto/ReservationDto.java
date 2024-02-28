package roomescape.web.dto;

import lombok.Getter;
import roomescape.domain.Reservation;

@Getter
public class ReservationDto {


    private Long id;
    private String name;
    private String date;
    private String time;

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


    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
    }
}