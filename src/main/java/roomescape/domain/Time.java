package roomescape.domain;

import lombok.Data;

@Data
public class Time {
    private long id;
    private String time;

    public Time(long id, String time) {
        this.id = id;
        this.time = time;
    }
}
