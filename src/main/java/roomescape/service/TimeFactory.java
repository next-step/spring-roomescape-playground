package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.dto.TimeDto;
import roomescape.model.Time;
import roomescape.model.Times;

@Component
public class TimeFactory {
    private final Times times;

    @Autowired
    public TimeFactory(Times times) {
        this.times = times;
    }

    public Time createTimeFromDto(TimeDto timeDto) {
        if (timeDto.id() == null) {
            throw new NullPointerException("time id가 null 입니다.");
        }

        return times.getTimeById(timeDto.id());
    }
}
