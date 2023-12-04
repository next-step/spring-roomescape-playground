package roomescape.application.dto;

public class TimeCreateRequest {

    private String time;

    public TimeCreateRequest() {
    }

    public TimeCreateRequest(final String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
