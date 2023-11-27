package roomescape.dto.request;

import java.time.LocalDate;

public class ReservationRequest {

    private final String name;
    private final LocalDate date;
    private final String time;

    private ReservationRequest(String name, LocalDate date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
