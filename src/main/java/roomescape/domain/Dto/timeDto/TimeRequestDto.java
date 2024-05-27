package roomescape.domain.Dto.timeDto;

import lombok.Getter;
import lombok.Data;
import roomescape.domain.Model.Time;

import java.time.LocalTime;

@Data
public class TimeRequestDto {
    private String time;

    public Time toTime(){
        return new Time(time);
    }
}
