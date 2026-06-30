package roomescape.domain;

import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    private Reservation(String name, LocalDate date, Time time, Long id) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(String name, LocalDate date, Time time) {
        validateReservationDateTime(date, time);
        return new Reservation(name, date, time, null);
    }

    public static Reservation restore(String name, LocalDate date, Time time) {
        return new Reservation(name, date, time, null);
    }

    public static Reservation withId(Reservation reservation, Long id) {
        return new Reservation(reservation.name, reservation.date, reservation.time, id);
    }

    private static void validateReservationDateTime(LocalDate date, Time time) {
        LocalDateTime reservationDateTime = date.atTime(time.getTime());

        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new RoomEscapeException(ErrorCode.INVALID_RESERVATION_TIME);
        }
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
