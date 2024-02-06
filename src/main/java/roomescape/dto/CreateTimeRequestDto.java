package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTimeRequestDto {
    private final String time;

    @JsonCreator
    public CreateTimeRequestDto(@JsonProperty("time") String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }
}
