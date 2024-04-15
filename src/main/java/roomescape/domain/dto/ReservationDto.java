package roomescape.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ReservationDto {

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String date;

    @NotBlank
    @Pattern(regexp = "\\d{2}:\\d{2}")
    private String time;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
  
    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setTime(String time) {
        this.time = time;
    }
}
