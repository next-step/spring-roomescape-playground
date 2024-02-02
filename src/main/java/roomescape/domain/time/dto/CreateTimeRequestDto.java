package roomescape.domain.time.dto;

import jakarta.validation.constraints.NotEmpty;

public class CreateTimeRequestDto {

    @NotEmpty
    String time;

    public String getTime() {
        return time;
    }
}
