package roomescape.time.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import roomescape.time.db.TimeEntity;

@Setter
@Getter
@Builder
public class TimeResponse {

    private Long id;
    private String time;

    public static TimeResponse from(TimeEntity timeEntity){
        TimeResponse dto = TimeResponse.builder()
                .id(timeEntity.getId())
                .time(timeEntity.getTime().toString())
                .build();
        return dto;
    }
}
