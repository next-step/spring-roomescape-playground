package roomescape.reservation.domain;

import roomescape.time.domain.Time;

public class Reservation {

    private Long id;
    private final String name;
    private final String date;
    private final Time time;


    public Reservation(Long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, Time time) {
        this(null, name, date, time);
    }

    void setId(Long id) {
        this.id = id;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String date() {
        return date;
    }

    public Time time() {
        return time;
    }
}
