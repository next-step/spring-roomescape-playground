package roomescape.domain.value;

import java.time.LocalTime;

public class Time {

    private LocalTime time;

    public Time(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
