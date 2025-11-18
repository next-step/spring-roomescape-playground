package roomescape.model;

public class Reservation {
    Long id;
    String name;
    String date;
    String time;


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

    private Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public static Reservation create(String name, String date, String time) {
        return new Reservation(null, name, date, time);
    }

    public static Reservation of(Long id, String name, String date, String time) {
        return new Reservation(id, name, date, time);
    }
}
