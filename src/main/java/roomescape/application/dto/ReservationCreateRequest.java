package roomescape.application.dto;

public class ReservationCreateRequest {
    private final String name;
    private final String date;
    private final Long time;

    public ReservationCreateRequest(final String name, final String date, final Long time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTime() {
        return time;
    }

}
