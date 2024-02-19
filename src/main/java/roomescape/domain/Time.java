package roomescape.domain;

import roomescape.exception.Time.TimeErrorMessage;
import roomescape.exception.Time.TimeException;

public class Time {

    private Long id;
    private String time;

    public Time(String time) {
        this(0L, time);
    }

    public Time(Long id, String time) {
        validateParams(time);
        this.id = id;
        this.time = time;
    }

    private void validateParams(String time) {
        if (time.isBlank()) {
            throw new TimeException(TimeErrorMessage.INVALID_DATA);
        }
    }

    public Time toEntity(Long id) {
        return new Time(id, this.time);
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
