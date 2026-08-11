package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public class ReservationRequest {

    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    @JsonCreator
    public ReservationRequest(
            @JsonProperty("name") String name,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("time") LocalTime time
    ) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toReservation() {
        return new Reservation(null, name, date, time);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
