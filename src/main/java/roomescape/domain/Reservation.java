package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import roomescape.exception.InvalidReservationException;

@Getter
public class Reservation {

    private final Integer id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    public Reservation(Integer id, String name, LocalDate date, Time time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate date, Time time) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
