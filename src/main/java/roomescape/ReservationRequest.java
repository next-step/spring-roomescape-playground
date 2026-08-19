package roomescape;

public class ReservationRequest {
    private final String date;
    private final String name;
    private final String time;

    public ReservationRequest(String date, String name, String time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
