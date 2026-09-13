package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReservationRequest {

    @NotBlank
    private final String name;

    @NotNull
    private final LocalDate date;

    @NotNull
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
