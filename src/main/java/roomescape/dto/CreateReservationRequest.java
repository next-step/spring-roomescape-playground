package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {

    private LocalDate date;
    private String name;
    private LocalTime time;

    public CreateReservationRequest(final LocalDate date, final String name, final LocalTime time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }
}
