package roomescape.domain;

import roomescape.exception.BlankReservationException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
        validateName(name);
        validateDate(date);
        validateTime(time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
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

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new BlankReservationException("이름을 입력해주세요");
        }
    }

    private static void validateDate(LocalDate date) {
        if (date == null) {
            throw new BlankReservationException("날짜를 선택해주세요");
        }
    }

    private static void validateTime(LocalTime time) {
        if (time == null) {
            throw new BlankReservationException("시간을 선택해주세요");
        }
    }
}

