package roomescape.domain;

import lombok.Getter;

@Getter
public class Time {
    private final Long id;
    private final String time;

    private Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static Time of(Long id, String time) {
        return new Time(id, time);
    }
}
