package roomescape.model;

public class Reservation
{
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

    public Reservation(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, String date, String time) {
        this(name, date, time);
        this.id = id;
    }
}
