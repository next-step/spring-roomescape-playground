package roomescape.dto;

import java.time.LocalDate;

public class ReservationRequest {

    private final String name;
    private final LocalDate date;
    private final Long time;

    public ReservationRequest(LocalDate date, String name, Long time) {
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

    public Long getTime() {
        return time;
    }
}
