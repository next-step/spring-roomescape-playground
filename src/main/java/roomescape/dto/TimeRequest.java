package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public class TimeRequest {
    @NotBlank
    private String time;

    protected TimeRequest() {};

    public String getTime() {
        return time;
    }
}
