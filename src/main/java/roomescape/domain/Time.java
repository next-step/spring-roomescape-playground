package roomescape.domain;

import roomescape.global.exception.BadRequestException;

import java.time.LocalTime;

import static roomescape.global.exception.ExceptionMessage.INVALID_TIME;

public class Time {

    private long id;

    private LocalTime time;

    protected Time() {
    }

    public Time(final long id, final LocalTime time) {
        validateTime(time);
        this.id = id;
        this.time = time;
    }

    public Time(final LocalTime time) {
        validateTime(time);
        this.time = time;
    }

    private void validateTime(final LocalTime time) {
        if (time == null) {
            throw new BadRequestException(INVALID_TIME.getMessage());
        }
    }

    public long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
