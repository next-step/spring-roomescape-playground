package roomescape.dto;

public class Time {

    private Long id;
    private String time;

    protected Time() {
    }

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Time(String time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public static Time toEntity(String time, Long id) {
        return new Time(id, time);
    }

}