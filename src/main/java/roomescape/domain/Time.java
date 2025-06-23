package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private final Long id;
    private final LocalTime time;

    private Time(Long id, LocalTime time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    public static Time create(LocalTime time) {
        return new Time(null, time);
    }

    public static Time of(Long id, LocalTime time) {
        return new Time(id, time);
    }

    private void validate(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 시간 정보는 비어있을 수 없습니다.");
        }
    }

    public Long getId() { return id; }

    public LocalTime getTime() { return time; }
}
