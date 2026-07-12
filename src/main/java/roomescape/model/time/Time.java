package roomescape.model.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Time {
    private Long id;
    private String time;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Time(@JsonProperty("time") String time) {
        this.time = time;
    }

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
