package roomescape.web.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public class TimeDto {
    private Long id;
    private String time;

    public Long getId() {
        return id;
    }
    public String getTime() {
        return time;
    }


    public TimeDto(Time time) {
        this.id = time.getId();
        this.time = time.getTime();
    }
}
