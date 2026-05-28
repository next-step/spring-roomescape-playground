package roomescape;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.exception.InvalidReservationException;

public class Time {
    private Long id;
    private LocalTime time;

    public Time(Long id, LocalTime time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    private static void validate(LocalTime time) {
        if (time == null) {
            throw new InvalidReservationException("필수 값이 누락되었습니다.");
        }
    }

    public boolean isSameTime(LocalTime time) {
        return this.time.equals(time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time that = (Time) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
