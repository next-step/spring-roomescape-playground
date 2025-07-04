package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import roomescape.exception.status.InvalidReservationException;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    private Reservation(Long id, String name, LocalDate date, Time time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(Long id, String name, LocalDate date, Time time) {
        return new Reservation(id, name, date, time);
    }

    private void validate(String name, LocalDate date, Time time) {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank()) {
            errors.add("이름은 필수입니다.");
        }
        if (date == null) {
            errors.add("날짜는 필수입니다.");
        }
        if (time == null) {
            errors.add("시간은 필수입니다.");
        }

        if (!errors.isEmpty()) {
            throw new InvalidReservationException(errors);
        }
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
}
