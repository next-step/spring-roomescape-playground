package roomescape.dto;

import org.springframework.format.annotation.DateTimeFormat;

public class ReservationRequest {
    private String name;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private String date;
    private Long time;

    private ReservationRequest() { }

    public ReservationRequest(final String name, final String date, final Long time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTime() {
        return time;
    }
}
