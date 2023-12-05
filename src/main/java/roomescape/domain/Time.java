package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private final Long id;
    private final LocalTime time;

    private Time(final TimeBuilder builder) {
        this.id = builder.id;
        this.time = builder.time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public static class TimeBuilder {
        private Long id;
        private LocalTime time;

        public TimeBuilder() {
        }

        public TimeBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public TimeBuilder time(final LocalTime time) {
            this.time = time;
            return this;
        }

        public Time build() {
            return new Time(this);
        }

    }
}
