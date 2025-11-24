package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Integer id, String name, LocalDate date, LocalTime time) {
    public static Reservation of(Integer id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation create(String name, LocalDate date, LocalTime time) {
        return new Reservation(null, name, date, time);
    }
}
