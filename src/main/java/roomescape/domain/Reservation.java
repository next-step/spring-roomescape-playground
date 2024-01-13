package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private String time;

    protected Reservation() {
    }

    public Reservation(String name, LocalDate date, String time) {
        this.id = null;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, LocalDate date, String time) {
        validateNotNull(name, date, time);
        validateNotBlank(name, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateNotNull(Object... parameters) {
        for (Object parameter : parameters) {
            if (Objects.isNull(parameter)) {
                throw new IllegalArgumentException("빈 인자가 있습니다");
            }
        }
    }

    private void validateNotBlank(String... strings) {
        for (String string : strings) {
            if (string.isBlank()) {
                throw new IllegalArgumentException("빈 인자가 있습니다");
            }
        }
    }

    public Reservation with(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
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

    public String getTime() {
        return time;
    }
}
