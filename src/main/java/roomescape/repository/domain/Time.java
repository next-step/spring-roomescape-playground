package roomescape.repository.domain;

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

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Time(String id, String time) {
        this.id = Long.parseLong(id);
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
