package roomescape.controller.dto;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;


public class ReservationResponseDto {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationResponseDto(){
    }



    public ReservationResponseDto(Reservation reservation) {
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
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
}
