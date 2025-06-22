package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(Long id, String name,  LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (date == null || time == null) {
            throw new IllegalArgumentException("날짜 및 시간은 필수입니다.");
        }
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
}
