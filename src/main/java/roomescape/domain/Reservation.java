package roomescape.domain;

import java.time.LocalDate;

public record Reservation(long id, String name, LocalDate date, Time time) {

    public Reservation {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (date == null) {
            throw new IllegalArgumentException();
        }

        if (time == null) {
            throw new IllegalArgumentException();
        }

    }
}
