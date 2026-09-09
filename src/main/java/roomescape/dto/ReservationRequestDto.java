package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public class ReservationRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String date;

    private int time;

    public ReservationRequestDto(String name, String date, int time) {
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

    public int getTime() {
        return time;
    }
}


