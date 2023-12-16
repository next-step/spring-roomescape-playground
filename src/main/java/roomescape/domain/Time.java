package roomescape.domain;

public class Time {
    private Long id;
    private String time;

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Time() {
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
