package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class CreateTimeRequestDto {
    @NotBlank(message = "Time is required")
    private final String time;

    @JsonCreator
    public CreateTimeRequestDto(@JsonProperty("time") String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }
}
