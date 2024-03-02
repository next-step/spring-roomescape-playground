package roomescape.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Time {

    private Long id;
    private String time;

    public Time(Long id, String time){
        this.id = id;
        this.time = time;
    }
}
