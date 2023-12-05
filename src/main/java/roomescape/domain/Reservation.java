package roomescape.domain;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    private Reservation(final ReservationBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.date = builder.date;
        this.time = builder.time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public static class ReservationBuilder {
        private Long id;
        private final String name;
        private final LocalDate date;
        private final Time time;

        public ReservationBuilder(final String name, final LocalDate date, final Time time) {
            this.name = name;
            this.date = date;
            this.time = time;
        }

        public ReservationBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Reservation build() {
            return new Reservation(this);
        }

    }
}
