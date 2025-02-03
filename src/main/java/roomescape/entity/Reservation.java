package roomescape.entity;

import roomescape.entity.value.Date;
import roomescape.entity.value.Name;
import roomescape.entity.value.Time;

public class Reservation {

    private final Long id;
    private final Name name;
    private final Date date;
    private final Time time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = Name.of(name);
        this.date = Date.of(date);
        this.time = Time.of(time);
    }

    public Reservation(String name, String date, String time) {
        this.id = null;
        this.name = Name.of(name);
        this.date = Date.of(date);
        this.time = Time.of(time);
    }

    private Reservation() {
        this.id = null;
        this.name = null;
        this.date = null;
        this.time = null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getDate() {
        return date.getValue();
    }

    public String getTime() {
        return time.getValue();
    }


}
