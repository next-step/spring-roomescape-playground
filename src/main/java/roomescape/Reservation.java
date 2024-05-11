package roomescape;

import java.sql.Time;
import java.sql.Date;

public class Reservation {
    private Long id;
    private String name;
    private Date date;
    private Time time;

    public Reservation() {

    }

    public Reservation(Long id, String name, Date date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, Date date, Time time) {
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

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }


}
