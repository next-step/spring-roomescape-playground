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
        validateName(name);
        validateDate(date);
        validateTime(time);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ReservationException("[ERROR] 예약자 이름을 입력해주세요.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new ReservationException("[ERROR] 예약 날짜를 선택해주세요.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new ReservationException("[ERROR] 지난 날짜로는 예약할 수 없습니다. 오늘 혹은 이후 날짜를 선택해주세요.");
        }
    }

    private void validateTime(Time time) {
        if (time == null) {
            throw new ReservationException("[ERROR] 예약 시간을 선택해주세요.");
        }
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public Time getTime() { return time; }
}
