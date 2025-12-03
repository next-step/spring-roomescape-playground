package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private Long id;
    private LocalTime startTime;

    protected Time() {
    }

    private Time(Long id, LocalTime startTime) {
        validate(startTime);
        this.id = id;
        this.startTime = startTime;
    }

    public static Time of(Long id, LocalTime startTime) {
        return new Time(id, startTime);
    }

    private void validate(LocalTime startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("시작 시간은 필수입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
