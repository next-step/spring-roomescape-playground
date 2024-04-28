package roomescape.entity;

public class Time {
    private long id;
    private String time;
    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }


    public Time() {}

    public String getTime() {
        return time;
    }
    public long getId() {return id;}
}
