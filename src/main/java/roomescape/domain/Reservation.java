package roomescape.domain;

import java.time.LocalDate;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        Time time
) {

    public static Reservation of(final Long id, final String name, final LocalDate date, final Time time) {
        return new Reservation(id, name, date, time);
    }
}
