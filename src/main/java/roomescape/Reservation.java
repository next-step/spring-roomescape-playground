package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import roomescape.exception.InvalidReservationException;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public static Reservation create(long id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
    }

    private Reservation(long id, String name, LocalDate date, LocalTime time) {
        boolean hasEmptyName = Stream.of(
            name
        ).anyMatch(value -> value == null || value.isBlank());
        boolean hasEmptyDateAndTime = Stream.of(
            date,
            time
        ).anyMatch(value -> value == null);
        if (hasEmptyName || hasEmptyDateAndTime) {
            throw new InvalidReservationException("예약 정보는 비어 있을 수 없습니다.");
        }
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

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", date='" + date + '\'' +
            ", time='" + time + '\'' +
            '}';
    }
}
