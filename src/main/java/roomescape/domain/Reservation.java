package roomescape.domain;

import java.util.HashMap;
import java.util.Map;

public class Reservation {
    private final String name;
    private final String date;
    private final Time time;
    private long id;

    public Reservation(long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(long id, String name, String date, Time time) {
        return new Reservation(id, name, date, time);
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

    public Time getTime() {
        return time;
    }

}
