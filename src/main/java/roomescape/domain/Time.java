package roomescape.domain;

public class Time {
    private final Long id;
    private final String time;

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return this.id;
    }

    public String getTime() {
        return this.time;
    }

}
