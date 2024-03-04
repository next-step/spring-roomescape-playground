package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Time {

    private Long id;
    private String time;

    public Time() {

    }

    public Time(String time) {
        this.time = time;
    }

    @JsonCreator
    public Time(@JsonProperty("id") Long id,
                @JsonProperty("time") String time) {
        this.id = id;
        this.time = time;
    }

    @Override
    public String toString() {
        return this.time;
    }

    public String getTime() {
        return this.time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

}
