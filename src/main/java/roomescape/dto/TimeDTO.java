package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeDTO {

    private Long id;
    private String time;

    public TimeDTO() {
    }

    @JsonCreator
    public TimeDTO(@JsonProperty("id") Long id,
                   @JsonProperty("time") String time) {
        this.id = id;
        this.time = time;
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

    @Override
    public String toString() {
        return this.time;
    }
}

