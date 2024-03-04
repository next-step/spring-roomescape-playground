package roomescape.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class Time {
    private Long id;
    private String time;

    public Time() {
    }

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getTime()  // 반환 타입을 String으로 변경
    {
        return time;
    }

    public boolean notEmpty() {
        return notNullOrEmpty(time);
    }

    private boolean notNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
