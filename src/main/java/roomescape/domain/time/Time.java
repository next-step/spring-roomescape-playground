package roomescape.domain.time;

import java.time.LocalTime;

public class Time {
    private long id;
    private final LocalTime time;

    private final String INVALID_TIME_FORMAT = "시간을 정확하게 입력해주세요.";

    public Time(String time) {
        this.time = validateTime(time);
    }

    public LocalTime validateTime(String time) {
        if(time.isBlank() || time.isEmpty()) {
            throw new IllegalArgumentException(INVALID_TIME_FORMAT);
        }
        return LocalTime.parse(time);
    }

    public LocalTime getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
