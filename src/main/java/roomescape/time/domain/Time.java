package roomescape.time.domain;

import java.time.LocalTime;

public class Time {
    private Long id;
    private LocalTime value;

    public Time(LocalTime value) {
        this.value = value;
    }

    private Time(Long id, LocalTime value) {
        this.id = id;
        this.value = value;
    }

    public Time withId(Long id) {
        return new Time(id, this.value);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getValue() {
        return value;
    }
}
