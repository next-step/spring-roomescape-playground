package roomescape;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReservationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String date;

    @NotNull
    @Positive
    private Long time;

    public ReservationRequest() {
    }

    public ReservationRequest(String name, String date, Long time) {
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
