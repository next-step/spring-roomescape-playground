package roomescape.dto.response;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeCreateResponse {
    private final Long id;
    private final LocalTime time;

    public TimeCreateResponse(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }
}
