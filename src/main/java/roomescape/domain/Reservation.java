package roomescape.domain;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.exception.InvalidReservationException;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation(Long id, String name, LocalDate date, Time time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private static void validate(String name, LocalDate date, Time time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new InvalidReservationException("필수 값이 누락되었습니다.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidReservationException("과거 날짜로 예약할 수 없습니다.");
        }
    }

    public boolean isSameTime(LocalDate date, Time time) {
        return this.date.equals(date) && this.time.equals(time);
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

    public Time getTimeInfo() {return time;}

    public LocalTime getTime() {return time.getTime();}

    public Long getTimeId() {return time.getId();}

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
