package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    @NotBlank
    private String name;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Long timeId;

    protected ReservationRequest() {
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
