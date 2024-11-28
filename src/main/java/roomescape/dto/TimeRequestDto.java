package roomescape.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeRequestDto {
    private String time;

    public TimeRequestDto() {}

    public TimeRequestDto(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public LocalTime toLocalTime() { //string -> localTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, formatter);
    }
}
