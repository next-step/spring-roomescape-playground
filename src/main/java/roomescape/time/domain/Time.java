package roomescape.time.domain;

import jakarta.annotation.Nonnull;

import java.time.LocalTime;

public class Time {
    private final TimeId id;
    private final LocalTime time;

    public Time(@Nonnull TimeId id, @Nonnull LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public @Nonnull TimeId getId() {
        return id;
    }

    public @Nonnull LocalTime getTime() {
        return time;
    }
}
