package roomescape.model.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeDTO {
    private String time;

    @JsonCreator
    public TimeDTO(@JsonProperty("time") String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Time toTime() {
        return new Time(this.time);
    }
}
