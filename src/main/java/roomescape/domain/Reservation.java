package roomescape.domain;

import roomescape.exception.ReservationErrorCode;
import roomescape.exception.ReservationException;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Reservation {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z]+( [가-힣a-zA-Z]+)*$");

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final Time time;

    public Reservation(Long id, String name, LocalDate date, Time time) {
        validateName(name);
        validateDate(date);
        validateTime(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, Time time) {
        this(null, name, date, time);
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

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_INVALID);
        }

        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_INVALID);
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_INVALID);
        }
    }

    private void validateTime(Time time) {
        if (time == null || time.getId() == null) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_INVALID);
        }
    }
}
