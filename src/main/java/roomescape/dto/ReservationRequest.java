package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, @JsonProperty("time") Long timeId) {
}
