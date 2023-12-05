package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class Time {
    private long id;
    private LocalTime time;

    @JsonCreator
    public Time(
            @JsonProperty("id") long id,
            @JsonProperty("time") LocalTime time
    ) {
        this.id = id;
        this.time = time;
    }

    public void setId(long generatedId) {
        this.id = generatedId;
    }

    public String getTimeAsString() {
        return time.toString();
    }

    public static LocalTime parseTime(String timeString) {
        return LocalTime.parse(timeString);
    }
}
