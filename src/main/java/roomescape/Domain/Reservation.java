package roomescape.Domain;

public class Reservation {
    private long id;
    private String name;
    private String date;
    private Time time;

    public Reservation() {
    }

    public Reservation(long id, String name, String date, Time time) {
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

    public boolean notEmpty() {
        return notNullOrEmpty(name) && notNullOrEmpty(date) && time != null;
    }

    private boolean notNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
