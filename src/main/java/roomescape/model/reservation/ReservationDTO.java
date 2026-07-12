package roomescape.model.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationDTO {
    private String name;
    private String date;

    @JsonProperty("time")
    private Long timeId;

    public ReservationDTO(String name, String date, Long timeId) {
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
