package roomescape.domain;

public class Reservation {
    private int id;
    private String name;
    private String date;
    private Time time;

    public Reservation(int id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
