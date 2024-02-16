package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TimeAddRequest {
    @NotBlank
    private final String time;
    @NotNull
    private long id;

    public TimeAddRequest(long id, String time) {
        this.id = id;
        this.time = time;
    }
    public String getTime() {
        return this.time;
    }

    public long getId() {
        return this.id;
    }
}