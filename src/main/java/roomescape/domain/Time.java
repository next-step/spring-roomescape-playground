package roomescape.domain;

import roomescape.exception.InvalidTimeException;

public class Time {
    private Long id;
    private String time;

    public Time(Long id, String time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    private void validate(String time) {
        if (isNullOrBlank(time)) {
            throw new InvalidTimeException("시간은 필수.");
        }
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
