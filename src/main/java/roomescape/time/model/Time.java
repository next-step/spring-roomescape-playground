package roomescape.time.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
public class Time {

    @Setter
    private Long id;
    private LocalTime time;

    public Time(LocalTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Time that)) return false;

        return this.getTime().equals(that.getTime());
    }
}