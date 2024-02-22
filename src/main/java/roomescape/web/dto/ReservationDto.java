package roomescape.web.dto;

import lombok.Builder;
import roomescape.domain.Reservation;

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
    
    public Reservation toEntity() {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setName(name);
        reservation.setDate(date);
        reservation.setTime(time);

        return reservation;
    }
}