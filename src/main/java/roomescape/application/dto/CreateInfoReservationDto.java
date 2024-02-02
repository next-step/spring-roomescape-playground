package roomescape.application.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateInfoReservationDto {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Long timeId;
    private final LocalTime time;

    private CreateInfoReservationDto(final Long id, final String name, final LocalDate date, final Long timeId, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.time = time;
    }

    public static CreateInfoReservationDto from (final Reservation reservation) {
        return new CreateInfoReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getId(),
                reservation.getTime().getTime()
        );
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

    public Long getTimeId() {
        return timeId;
    }

    public LocalTime getTime() {
        return time;
    }
}
