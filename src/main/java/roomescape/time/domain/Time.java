package roomescape.time.domain;

import java.time.LocalTime;

public class Time {

    private final Long id;
    private final LocalTime value;

    public Time(Long id, String value) {
        this.id = id;
        this.value = LocalTime.parse(value);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getValue() {
        return value;
    }

    public String getFormattedTime() {
        return value.toString();
    }
}