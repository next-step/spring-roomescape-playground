package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Reservation {
    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    @JsonCreator
    public Reservation(
            @JsonProperty("id") long id,
            @JsonProperty("name") String name,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("time") LocalTime time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }


    public long getId() {
        return id;
    }

}
