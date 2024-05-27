package roomescape.time.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
public class TimeRequest {

    @NotBlank
    private String time;
}
