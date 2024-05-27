package roomescape.dto;

import roomescape.domain.Times;
import roomescape.domain.TimeFormatter;

import java.time.LocalTime;

public record SaveTimeRequest(
        String time
) {

    public Times toTimes(){
        LocalTime time = LocalTime.parse(this.time, TimeFormatter.timeFormatter);
        return new Times(time);
    }
}
