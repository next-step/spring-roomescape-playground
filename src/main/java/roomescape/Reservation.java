package roomescape;

import roomescape.exception.InvalidReservationException;

public class Reservation {
    private long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(long id, String name, String date, String time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, String date, String time){
        if (name == null || name.isEmpty() ||
                date == null || date.isEmpty() ||
                time == null || time.isEmpty()) {
            throw new InvalidReservationException();
        }
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
}
