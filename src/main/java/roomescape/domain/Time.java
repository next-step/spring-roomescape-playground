package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private int id;
    private LocalTime time;

    public Time() {
    }

    public Time(int id, LocalTime time) {
        this.id = id;
        this.time = time;
    }
}
