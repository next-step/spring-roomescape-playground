package roomescape.domain;

public class Time {

    private long id;

    private String time;

    protected Time() {
    }

    public Time(final long id, final String time) {
        this.id = id;
        this.time = time;
    }

    public Time(final String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
