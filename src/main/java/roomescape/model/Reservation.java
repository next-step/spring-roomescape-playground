package roomescape.model;

public class Reservation {

    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
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
