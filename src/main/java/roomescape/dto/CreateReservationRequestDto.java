package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateReservationRequestDto {
    @NotBlank(message = "Name is required")
    private final String name;

    @NotBlank(message = "Date is required")
    private final String date;

    @NotNull(message = "Time ID is required")
    private final Long timeId;

    @JsonCreator
    public CreateReservationRequestDto(@JsonProperty("name") String name,
                                       @JsonProperty("date") String date,
                                       @JsonProperty("timeId") Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public Long getTimeId() {
        return timeId;
    }
}
