package roomescape;

import java.util.stream.Stream;
import roomescape.exception.InvalidReservationException;

public class Reservation {

    private final long id;
    private final String name;
    private final String date;
    private final String time;

    public static Reservation create(long id, String name, String date, String time) {
        return new Reservation(id, name, date, time);
    }

    private Reservation(long id, String name, String date, String time) {
        boolean hasEmpty = Stream.of(
            name,
            date,
            time
        ).anyMatch(value -> value == null || value.isBlank());
        if (hasEmpty) {
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

    public String getDate() {
        return date;
    }

    public String getTime() {
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
