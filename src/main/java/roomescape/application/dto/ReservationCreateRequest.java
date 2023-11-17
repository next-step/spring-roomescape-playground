package roomescape.application.dto;

public class ReservationCreateRequest {
    private final String name;
    private final String date;
    private final String time;

    public ReservationCreateRequest(final String name, final String date, final String time) {
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

    public String getTime() {
        return time;
    }
}
