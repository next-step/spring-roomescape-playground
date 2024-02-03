package roomescape.domain;

public class Time {

    private Long id;
    private String time;

    protected Time() {
    }

    public Time(String time) {
        this(null, time);
    }

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Time with(Long id) {
        return new Time(id, time);
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
