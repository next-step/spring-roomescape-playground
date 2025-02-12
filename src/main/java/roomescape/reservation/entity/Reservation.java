package roomescape.reservation.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private int id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    private Reservation() {
    }

    private Reservation(int id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(int id, String name) {
        return new Reservation(id, name, LocalDate.now(), LocalTime.now());
    }

    public static Reservation ofTime(int id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
    }

    public int getId() {
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
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
