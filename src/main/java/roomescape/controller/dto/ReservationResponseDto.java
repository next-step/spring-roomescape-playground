package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;


public class ReservationResponseDto {
    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    public LocalTime getTime() {
        return time;
    }
}
