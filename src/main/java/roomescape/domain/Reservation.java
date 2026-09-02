package roomescape.domain;

import roomescape.exception.InvalidReservationException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {
    private static final int MAX_NAME_LENGTH = 20;

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    public Reservation(Long id, String name, LocalDate date, Time time) {
        validateRequiredValues(name, date, time);
        validateNameLength(name);
        validateFutureDateTime(date, time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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

    private void validateRequiredValues(String name, LocalDate date, Time time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new InvalidReservationException("예약 정보는 모두 입력해야 합니다.");
        }
    }

    private void validateNameLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new InvalidReservationException("예약자 이름은 20자 이하여야 합니다.");
        }
    }

    private void validateFutureDateTime(LocalDate date, Time time) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time.getTime());

        if (!reservationDateTime.isAfter(LocalDateTime.now())) {
            throw new InvalidReservationException("올바른 예약 날짜와 시간을 선택해야 합니다.");
        }
    }
}
