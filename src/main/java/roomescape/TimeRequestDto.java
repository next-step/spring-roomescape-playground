package roomescape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.thymeleaf.util.StringUtils;

public class TimeRequestDto {
    private String time;
    @JsonCreator
    public TimeRequestDto(@JsonProperty("time") String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
