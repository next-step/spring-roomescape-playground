package roomescape.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        Objects.requireNonNull(name, "name은 필수입니다.");
        Objects.requireNonNull(date, "date는 필수입니다.");
        Objects.requireNonNull(time, "time은 필수입니다.");
        if (name.isBlank()) throw new IllegalArgumentException("name은 빈 값일 수 없습니다.");
    }
}