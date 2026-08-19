package roomescape;

import jakarta.validation.constraints.NotBlank;

public class ReservationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String date;

    @NotBlank
    private String time;

    public ReservationRequest() {
    }

    public ReservationRequest(String name, String date, String time) {
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

    public String getTime() {
        return time;
    }
}
