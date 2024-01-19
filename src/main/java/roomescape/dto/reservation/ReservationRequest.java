package roomescape.dto.reservation;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @NotNull
    private String name;

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
