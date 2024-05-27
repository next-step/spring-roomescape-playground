package roomescape.Model;

public class Time {
    private long id;
    private String time;

    public Time() {

    }

    public Time(long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Time(String time) {
        this.time = time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public long getId() {
        return id;
    }
}
