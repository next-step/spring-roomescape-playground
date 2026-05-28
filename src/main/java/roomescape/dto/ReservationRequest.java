package roomescape.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationRequest {
    private String name;
    private LocalDate date;

    @JsonProperty("time")
    private Long timeId;

    public ReservationRequest() {
    }

    public ReservationRequest(String name, LocalDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public Long getTimeId() { return timeId; }
}
