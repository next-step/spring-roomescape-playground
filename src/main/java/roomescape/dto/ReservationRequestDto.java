package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public class ReservationRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String date;

    private Long time;

    public ReservationRequestDto(String name, String date, Long time) {
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


