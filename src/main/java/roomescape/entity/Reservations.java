package roomescape.entity;

public class Reservations {

    private long id;
    private String name;
    private String date;
    private Time time;

    public Reservations(long id, String name, String date, Time time) {
        this.id = id;
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

    public Time getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

}
