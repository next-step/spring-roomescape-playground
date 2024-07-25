package roomescape.domain;

public class Time {
    private int id;
    private String time;

    public Time(int id, String time) {
        this.id = id;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }
}
