package roomescape.application.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationDto {

    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public CreateReservationDto(final String name, final LocalDate date, final LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toEntity() {
        return new Reservation(null, name, date, time);
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
