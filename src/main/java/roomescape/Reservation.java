package roomescape;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(Long id, String name, String data, String time) {
        this.id = id;
        this.name = name;
        this.date = data;
        this.time = time;
    }

    public Long getId() {
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

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
