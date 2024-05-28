package roomescape.time.dto;

import lombok.Getter;
import lombok.Data;
import roomescape.time.domain.Time;


@Data
public class TimeRequestDto {
    private String time;

    public Time toTime(){
        return new Time(time);
    }
}
