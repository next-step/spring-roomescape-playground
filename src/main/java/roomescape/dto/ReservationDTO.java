package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationDTO {

    private Long id;
    private String name;
    private String date;
    private TimeDTO time;

    @JsonCreator
    public ReservationDTO(@JsonProperty("id") Long id,
                          @JsonProperty("name") String name,
                          @JsonProperty("date") String date,
                          TimeDTO time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public TimeDTO getTime() {
        return this.time;
    }
}

