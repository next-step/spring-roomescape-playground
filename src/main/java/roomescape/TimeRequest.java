package roomescape;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class TimeRequest {

    @NotBlank
    @Pattern(regexp = "([01][0-9]|2[0-3]):[0-5][0-9]")
    private String time;

    public TimeRequest() {
    }

    public String getTime() {
        return time;
    }
}
