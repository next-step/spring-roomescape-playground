package roomescape.application.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateInfoReservationDto {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final CreateInfoTimeInReservationDto time;

    private CreateInfoReservationDto(final Long id, final String name, final LocalDate date, final CreateInfoTimeInReservationDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static CreateInfoReservationDto from (final Reservation reservation) {
        return new CreateInfoReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                CreateInfoTimeInReservationDto.from(reservation.getTime())
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
        return time.getId();
    }

    public LocalTime getTime() {
        return time.getTime();
    }

    static class CreateInfoTimeInReservationDto {

        private final Long id;
        private final LocalTime time;

        private CreateInfoTimeInReservationDto(final Long id, final LocalTime time) {
            this.id = id;
            this.time = time;
        }

        public static CreateInfoTimeInReservationDto from(final Time time) {
            return new CreateInfoTimeInReservationDto(time.getId(), time.getTime());
        }

        public Long getId() {
            return id;
        }

        public LocalTime getTime() {
            return time;
        }
    }
}
