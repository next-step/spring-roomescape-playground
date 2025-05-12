package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.exception.InvalidReservationException;

public class Reservation {

    private final Integer id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Integer id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation() {
        this.id = null;
        this.name = null;
        this.date = null;
        this.time = null;
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidReservationException("이름은 필수입니다.");
        }
        if (date == null || time == null) {
            throw new InvalidReservationException("날짜와 시간은 필수입니다.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidReservationException("과거 날짜로 예약할 수 없습니다.");
        }
    }

    public Integer getId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
