package roomescape.domain;

import roomescape.exception.ReservationException;

import java.time.LocalDate;

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

    public static Reservation create(String name, LocalDate date, Time time) {
        return new Reservation(null, name, date, time);
    }

    public static Reservation of(Long id, String name, LocalDate date, Time time) {
        return new Reservation(id, name, date, time);
    }

    private void validate(String name, LocalDate date, Time time) {
        validateRequiredFields(name, date, time);
        validateBusinessRules(date);
    }

    private void validateRequiredFields(String name, LocalDate date, Time time) {
        if (name == null || name.isBlank()) {
            throw new ReservationException("[ERROR] name 필드는 비어있을 수 없습니다.");
        }
        if (date == null) {
            throw new ReservationException("[ERROR] date 필드는 null일 수 없습니다.");
        }
        if (time == null) {
            throw new ReservationException("[ERROR] time 필드는 null일 수 없습니다.");
        }
    }

    private void validateBusinessRules(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new ReservationException("[ERROR] 예약 날짜는 과거일 수 없습니다.");
        }
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public Time getTime() { return time; }
}
