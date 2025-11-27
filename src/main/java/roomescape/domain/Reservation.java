package roomescape.domain;

import roomescape.exception.FailMessage;
import roomescape.exception.InvalidReservationArgumentException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    private Reservation(Long id, String name, String date, String time) {

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation newReservationFromDb(Long id, String name, String date, String time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation createReservation(Long id, String name, String stringDate, String stringTime) {
        LocalDate date = LocalDate.parse(stringDate);
        LocalTime time = LocalTime.parse(stringTime);

        if (date.isBefore(LocalDate.now())) {
            throw new InvalidReservationArgumentException(FailMessage.BAD_REQUEST);
        }

        if (date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now())) {
            throw new InvalidReservationArgumentException(FailMessage.BAD_REQUEST);
        }

        return new Reservation(id, name, stringDate, stringTime);

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

    public String getTime() {

        return time;
    }
}
