package roomescape.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private static AtomicLong ID_INDEX = new AtomicLong(1);

    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(String name, String date, String time) {
        id = ID_INDEX.getAndIncrement();
        this.name = name;
        this.date = date;
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
}
