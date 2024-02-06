package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateReservationRequestDto {
    private final String name;
    private final String date;
    private final Long timeId;

    @JsonCreator
    public CreateReservationRequestDto(@JsonProperty("name") String name,
                                       @JsonProperty("date") String date,
                                       @JsonProperty("timeId") Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public Long getTimeId() {
        return timeId;
    }
}
