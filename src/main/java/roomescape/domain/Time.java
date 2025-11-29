package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private Long id;
    private LocalTime startTime;
    private boolean available;

    protected Time() {
    }

    private Time(Long id, LocalTime startTime, boolean available) {
        validate(startTime);
        this.id = id;
        this.startTime = startTime;
        this.available = available;
    }

    public static Time create(LocalTime startTime) {
        return new Time(null, startTime, true);
    }

    public static Time of(Long id, LocalTime startTime, Boolean available) {
        return new Time(id, startTime, available);
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

    public boolean isAvailable() {
        return available;
    }
}
