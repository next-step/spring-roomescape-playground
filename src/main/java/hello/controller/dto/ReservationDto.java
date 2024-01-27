package hello.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hello.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {

    private Long id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public ReservationDto(){}

    public ReservationDto(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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

    public static ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(reservation.getId(),
                reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
