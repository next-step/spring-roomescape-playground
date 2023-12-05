package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.cglib.core.Local;

@Getter
public class Reservation {
    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private long timeId; // Add this field to match the new table schema

    @JsonCreator
    public Reservation(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("time") LocalTime time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(long reservationId, String name, LocalDate date, long timeId, LocalTime time) {
        this.id = reservationId;
        this.name = name;
        this.date = date;
        this.time = time;
        this.timeId = timeId;

    }


    public void setId(long generatedId) {
        this.id = generatedId;
    }

    public void setTimeId(long timeId) {
        this.timeId = timeId;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
