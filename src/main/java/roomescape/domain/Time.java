package roomescape.domain;

public class Time {
    private final long id;
    private final String time;

    public Time(long id, String time) {
        this.id = id;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
