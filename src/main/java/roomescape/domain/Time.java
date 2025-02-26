package roomescape.domain;

import roomescape.global.exception.BadRequestException;

import static roomescape.global.exception.ExceptionMessage.INVALID_TIME;

public class Time {

    private long id;

    private String time;

    protected Time() {
    }

    public Time(final long id, final String time) {
        validateTime(time);
        this.id = id;
        this.time = time;
    }

    public Time(final String time) {
        validateTime(time);
        this.time = time;
    }

    private void validateTime(final String time) {
        if (time == null || time.isBlank()) {
            throw new BadRequestException(INVALID_TIME.getMessage());
        }
    }

    public long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
