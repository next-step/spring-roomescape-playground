package roomescape;

public class Reservation {

    private long id;
    private final String name;
    private final String date;
    private final String time;

    public static Reservation toEntity(ReservationRequest reservation, long id) {
        return new Reservation(
            id,
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime());
    }

    public Reservation(long id, String name, String date, String time) {
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
