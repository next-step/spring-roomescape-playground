package roomescape.domain;

import java.sql.Time;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {

    }
    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public static boolean isInvalid(Reservation reservation) {
        return (reservation.name == null || reservation.name.isBlank() ||
                reservation.date == null || reservation.date.isBlank() ||
                reservation.time == null || reservation.time.isBlank());
    }
}
