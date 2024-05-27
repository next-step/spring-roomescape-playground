package roomescape.model;

import lombok.Data;

@Data
public class Time {
    private int id;
    private String time;

    public Time() {
    }

    public Time(int id, String time) {
        this.id = id;
        this.time = time;
    }

    public Time(String time) {
        this.time = time;
    }
}
