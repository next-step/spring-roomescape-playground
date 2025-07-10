package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationRequest(
    @NotBlank(message = "{reservation.name.notBlank}")
    String name,

    @NotNull(message = "{reservation.date.notNull}")
    LocalDate date,

    @JsonProperty("time")
    @NotNull(message = "{reservation.timeId.notNull}")
    Long timeId
) {
}
