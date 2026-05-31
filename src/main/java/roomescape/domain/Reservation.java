package roomescape.domain;

import lombok.Getter;
import roomescape.exception.InvalidReservationException;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    public Reservation(String name, LocalDate date, Time time) {
        this(null, name, date, time);
    }

    public Reservation(Long id, String name, LocalDate date, Time time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate date, Time time) {
        Objects.requireNonNull(name, "name은 필수입니다.");
        Objects.requireNonNull(date, "date는 필수입니다.");
        Objects.requireNonNull(time, "time은 필수입니다.");
        if (name.isBlank()) throw new InvalidReservationException("name은 빈 값일 수 없습니다.");
    }
}
