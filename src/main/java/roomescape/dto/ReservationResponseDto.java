package roomescape.dto;

import roomescape.domain.Reservation;

public class ReservationResponseDto {

    private Long id;
    private String name;
    private String date;
    private TimeResponseDto time;

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = new TimeResponseDto(reservation.getTime());
    }

    public ReservationResponseDto(Long id, Reservation reservation) {
        this.id = id;
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = new TimeResponseDto(reservation.getTime());
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

    public TimeResponseDto getTime() {
        return time;
    }
}
