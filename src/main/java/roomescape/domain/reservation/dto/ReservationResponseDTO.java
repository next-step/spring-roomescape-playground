package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import roomescape.domain.time.domain.Time;

public class ReservationResponseDTO {
    Long id;
    String name;
    LocalDate date;
    Time time;

    ReservationResponseDTO() {

    }

    public ReservationResponseDTO(Long id, String name, LocalDate date, Time time) {
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

    public Time getTime() {
        return time;
    }
}
