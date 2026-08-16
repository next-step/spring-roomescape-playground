package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.exception.InvalidReservationRequestException;

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

    public Reservation withId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("예약 ID는 null일 수 없습니다.");
        }
        return new Reservation(id, name, date, time);
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new InvalidReservationRequestException();
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

    public LocalTime getTime() {
        return time;
    }
}
