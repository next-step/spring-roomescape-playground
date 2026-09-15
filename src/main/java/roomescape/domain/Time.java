package roomescape.domain;

import java.time.LocalTime;

public record Time(long id, LocalTime time) {

    public Time {

        if (time == null) {
            throw new IllegalArgumentException();
        }

    }
}
