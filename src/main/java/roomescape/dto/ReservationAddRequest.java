package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public class ReservationAddRequest {
    @NotBlank
    private final String name;
    @NotBlank
    private final String date;
    @NotBlank
    private final String time;

    public ReservationAddRequest(String name, String date, String time) {
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
