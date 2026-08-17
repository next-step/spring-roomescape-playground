package roomescape;

import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private AtomicLong id;
    private final String name;
    private String date;
    private String time;

    public static Reservation toEntity(Reservation reservation, long id) {
        return new Reservation(
            new AtomicLong(id),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime());
    }

    public Reservation(AtomicLong id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id.get();
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
