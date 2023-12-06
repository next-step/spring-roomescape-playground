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
    private Time time;

    @JsonCreator
    public Reservation(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("time") Time time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(long reservationId, String name, LocalDate date, Time time) {
        this.id = reservationId;
        this.name = name;
        this.date = date;
        this.time = time;
    }


    public void setId(long generatedId) {
        this.id = generatedId;
    }


    public void setTime(Time time) {
        this.time = time;
    }
}
