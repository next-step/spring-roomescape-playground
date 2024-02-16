package roomescape.domain;
public class Time {
    private final String time;

    private long id;

    public Time(long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static Time toEntity(long id, String time) {
        return new Time(id, time);
    }

    public String toString() {
        return this.time;
    }

    public String getTime() {
        return this.time;
    }

    public long getId() {
        return this.id;
    }
}