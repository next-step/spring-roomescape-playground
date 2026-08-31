package com.cholog.roomescape.roomescape.entity;

import com.cholog.roomescape.roomescape.exception.TimeNotValidException;

import java.time.LocalTime;
import java.util.Objects;

public class Time {

    private Long id;
    private LocalTime time;

    public Time() {
    }

    public Time(LocalTime time) {
        try {
            this.time = Objects.requireNonNull(time, "시간은 null 값일 수 없습니다.");
        } catch (NullPointerException e) {
            throw new TimeNotValidException(e.getMessage());
        }
    }

    private Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static Time withId(Long id, Time time) {
        return new Time(id, time.getTime());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
