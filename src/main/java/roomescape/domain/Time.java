package roomescape.domain;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Time {
    private Long id;
    private LocalTime time;

    public Time() {
    }

    public Time(Long id, String time) {
        this.id = id;
        this.time = LocalTime.parse(time);
    }

    public Time toEntity(Long id) {
        return new Time(id, this.time.toString());
    }
}
