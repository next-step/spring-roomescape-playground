package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import roomescape.exception.InvalidReservationException;

public class Reservation {

    private static final long UNSAVED_ID = 0L;
    private final long id;

    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public static Reservation create(String name, LocalDate date, LocalTime time) {
        return new Reservation(UNSAVED_ID, name, date, time);
    }

    public static Reservation create(long id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
    }

    private Reservation(long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private static void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new InvalidReservationException("예약 정보는 비어 있을 수 없습니다.");
        }
    }

    public Reservation withId(long id) {
        return new Reservation(id, name, date, time);
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
