package roomescape.domain;

import java.time.LocalTime;

public class TimeSlot {

    private Long id;
    private LocalTime startAt;

    private TimeSlot(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public TimeSlot(LocalTime startAt) {
        this(null, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public TimeSlot withId(Long id) {
        return new TimeSlot(id, startAt);
    }
}
