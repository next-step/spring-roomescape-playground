package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public static Reservation of(final Long id, final String name, final LocalDate date, final LocalTime time) {
        return new Reservation(id, name, date, time);
    }
}
