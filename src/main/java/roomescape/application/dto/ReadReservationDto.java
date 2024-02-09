package roomescape.application.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReadReservationDto {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReadTimeInReservationDto time;

    private ReadReservationDto(final Long id, final String name, final LocalDate date, final ReadTimeInReservationDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReadReservationDto from(final Reservation reservation) {
        return new ReadReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReadTimeInReservationDto.from(reservation.getTime())
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

    static class ReadTimeInReservationDto {

        private final Long id;
        private final LocalTime time;

        private ReadTimeInReservationDto(final Long id, final LocalTime time) {
            this.id = id;
            this.time = time;
        }

        public static ReadTimeInReservationDto from(final Time time) {
            return new ReadTimeInReservationDto(time.getId(), time.getTime());
        }

        public Long getId() {
            return id;
        }

        public LocalTime getTime() {
            return time;
        }
    }
}
