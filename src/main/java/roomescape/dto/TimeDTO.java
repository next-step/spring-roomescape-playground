package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeDTO {

    private Long id;
    private String time;

    public TimeDTO() {
    }

    public TimeDTO(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public TimeDTO(String id, String time) {
        this.id = Long.parseLong(id);
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id != null ? Long.parseLong(id) : null;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.time;
    }
}

