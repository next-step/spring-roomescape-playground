package roomescape.dto.response;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeGetResponse {
    private final Long id;
    private final LocalTime time;

    public TimeGetResponse(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

}
