package roomescape.time.domain;

public class Time {

    private final Long id;

    private final String time;

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long id() {
        return id;
    }

    public String time() {
        return time;
    }
}
