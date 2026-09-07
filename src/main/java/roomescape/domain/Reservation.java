package roomescape.domain;

import roomescape.exception.InvalidReservationException;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate reservedDate;
    private final Time time;

    private Reservation(Long id, String name, LocalDate reservedDate, Time time) {
        validate(name, reservedDate, time);
        this.id = id;
        this.name = name;
        this.reservedDate = reservedDate;
        this.time = time;
    }

    public Reservation(String name, LocalDate reservedDate, Time time) {
        this(null, name, reservedDate, time);
    }

    private void validate(String name, LocalDate reservedDate, Time time) {
        if (name == null || name.isBlank()) {
            throw new InvalidReservationException("예약자 이름은 필수입니다.");
        }
        if (reservedDate == null) {
            throw new InvalidReservationException("예약 날짜는 필수입니다.");
        }
        if (time == null) {
            throw new InvalidReservationException("예약 시간은 필수입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public Time getTime() {
        return time;
    }

    public Reservation withId(Long id) {
        return new Reservation(id, name, reservedDate, time);
    }
}
