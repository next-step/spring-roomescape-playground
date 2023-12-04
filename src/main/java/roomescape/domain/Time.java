package roomescape.domain;

import java.time.LocalDateTime;

public class Time {
    private final Long id;
    private final String time;

    public Time(long id, String time) {
        this.id = id;
        this.time =time;
    }

    public static Time of(long id, String time) {
        return new Time(id, time);
    }
}
