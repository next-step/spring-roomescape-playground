package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateRequest {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
