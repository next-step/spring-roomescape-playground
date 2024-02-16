package roomescape.dto;

public class TimeAddResponse {
    private final String time;

    private long id;

    public TimeAddResponse(long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static TimeAddResponse toDto(long id, String time) {
        return new TimeAddResponse(id, time);
    }

    public String getTime() {
        return time;
    }

    public long getId() {
        return id;
    }
}