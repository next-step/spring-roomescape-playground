package roomescape.dto;

public class TimeResponse {
    private Long id;
    private String time;

    public TimeResponse() {
    }

    public TimeResponse(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() { return id; }
    public String getTime() { return time; }
}
