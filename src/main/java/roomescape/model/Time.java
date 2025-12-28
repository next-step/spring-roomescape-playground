package roomescape.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;

public record Time(Integer id, @JsonProperty("time") LocalTime value) {
    public static Time of(Integer id, LocalTime value) {
        return new Time(id, value);
    }

    public static Time create(LocalTime value) {
        return new Time(null, value);
    }
}
