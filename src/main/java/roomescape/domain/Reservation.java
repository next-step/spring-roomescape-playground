package roomescape.domain;

import lombok.Getter;
import roomescape.exception.FailMessage;
import roomescape.exception.InvalidReservationArgumentException;

import java.time.LocalDate;

@Getter
public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final Time time;

    private Reservation(Long id, String name, String date, Time time) {

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(Long id, String name, String date, Time time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation create(Long id, String name, String stringDate, Time times) {
        LocalDate date = LocalDate.parse(stringDate);

        if (date.isBefore(LocalDate.now())) {
            throw new InvalidReservationArgumentException(FailMessage.BAD_REQUEST);
        }

        return new Reservation(id, name, stringDate, times);

    }
}
