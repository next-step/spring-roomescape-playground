package roomescape.domain;

import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final String date;
    private final Time time;

    private Reservation(String name, String date, Time time, Long id) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(String name, String date, Time time) {
        validateReservationDateTime(date, time);
        return new Reservation(name, date, time, null);
    }

    public static Reservation withId(Reservation reservation, Long id) {
        return new Reservation(reservation.name, reservation.date, reservation.time, id);
    }

    private static void validateReservationDateTime(String date, Time time) {
        LocalDate reservationDate = LocalDate.parse(date);
        LocalDateTime reservationDateTime = reservationDate.atTime(time.getTime());

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

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
