package roomescape.time.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import roomescape.time.db.Time;

@Setter
@Getter
@Builder
public class TimeResponse {

    private Long id;
    private String time;

    public static TimeResponse from(Time time) {
        TimeResponse dto = TimeResponse.builder()
                .id(time.getId())
                .time(time.getTime().toString())
                .build();
        return dto;
    }
}
