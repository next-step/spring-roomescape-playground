package roomescape.domain;

import roomescape.exception.InvalidTimeException;

public class Time {
    private Long id;
    private String time;

    public Time(Long id, String time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    private void validate(String time) {
        if (time == null || time.isBlank()) {
            throw new InvalidTimeException("시간은 필수입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
