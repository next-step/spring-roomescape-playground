package roomescape.model;

import java.time.LocalDate;

public record Reservation(Integer id, String name, LocalDate date, Time time) {
    public static Reservation of(Integer id, String name, LocalDate date, Time time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation create(String name, LocalDate date, Time time) {
        return new Reservation(null, name, date, time);
    }
}
