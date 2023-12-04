package roomescape.reservation;


import roomescape.Time.Time;

import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private Time time;

    public Reservation() {}

    public static Reservation toEntity(Reservation reservation) {
        Reservation reservation1 = new Reservation(reservation.name, reservation.date, reservation.time);
        return reservation1;
    }

    public String getName() {
        return name;
    }
    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Reservation(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public Reservation(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setTime (Time time) {this.time = time;}

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                '}';
    }
}
