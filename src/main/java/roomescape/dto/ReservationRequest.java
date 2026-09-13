package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequest(@NotBlank String name, @NotNull LocalDate date, @NotNull Long timeId) {

    @JsonCreator
    public ReservationRequest(
            @JsonProperty("date") LocalDate date,
            @JsonProperty("name") String name,
            @JsonProperty("time") Long timeId
    ) {
        this(name, date, timeId);
    }
}
