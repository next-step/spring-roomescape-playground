package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReservationRequest {

    @NotBlank
    private final String name;

    @NotNull
    private final LocalDate date;

    @NotNull
    private final Long timeId;

    @JsonCreator
    public ReservationRequest(
            @JsonProperty("date") LocalDate date,
            @JsonProperty("name") String name,
            @JsonProperty("time") Long timeId
    ) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
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
