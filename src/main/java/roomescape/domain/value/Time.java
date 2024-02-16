package roomescape.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
public class Time {

    private LocalTime time;

    public Time(String time) {
        this.time = LocalTime.parse(time);
    }
}
