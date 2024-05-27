package roomescape.time.dto;

import lombok.Getter;
import lombok.Setter;
import roomescape.time.domain.Time;

@Setter
@Getter
public class TimeResponse {
    private Long id;
    private String time;

    public static TimeResponse from(Time time) {
        TimeResponse dto = new TimeResponse();
        dto.setId(time.getId());
        dto.setTime(time.getTime().toString());
        return dto;
    }
}
